package learn.mastery.data;

import learn.mastery.models.Guest;
import learn.mastery.models.Host;
import learn.mastery.models.Reservation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ReservationFileRepository implements ReservationRepository{

    private static final String HEADER = "id,start_date,end_date,guest_id,total";
    private static final String DELIMITER = ",";
    private final String directory;
    private final GuestRepository guestRepository;

    public ReservationFileRepository(@Value("${reservationRepository}") String directory, GuestRepository guestRepository) {
        this.directory = directory;
        this.guestRepository = guestRepository;
    }

    @Override
    public List<Reservation> findAll() throws DataException {
        List<Reservation> result = new ArrayList<>();

        try {
            Files.list(Paths.get(directory))
                    .filter(p -> p.toString().endsWith(".csv"))
                    .forEach(path -> {
                        String filename = path.getFileName().toString();
                        String hostId = filename.replace(".csv", "");
                        Host host = new Host();
                        host.setId(hostId);
                        try {
                            result.addAll(findByHostId(host));
                        } catch (DataException e) {
                            // log error
                        }
                    });
        } catch (IOException e) {
            throw new DataException(e);
        }

        return result;
    }

    @Override
    public Reservation findById(int id, Host host) throws DataException {
        return findByHostId(host).stream()
                .filter(r -> r.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Reservation> findByHostId(Host host) throws DataException {
        List<Reservation> result = new ArrayList<>();
        Path reservationFilePath = Paths.get(directory, host.getId() + ".csv");

        if (!Files.exists(reservationFilePath)) {
            return result;
        }

        try (BufferedReader reader = Files.newBufferedReader(reservationFilePath)) {
            reader.readLine(); //skip header
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                Reservation res = deserialize(line);

                if (res != null) {
                    res.setHost(host);
                }
                result.add(res);
            }
        } catch (IOException ex) {
            throw new DataException(ex);
        }

        return result.stream()
                .sorted(Comparator.comparing(Reservation::getStart_date))
                .collect(Collectors.toList());
    }

    @Override
    public Reservation create(Reservation reservation) throws DataException {

        if (reservation == null) {
            return null;
        }

        List<Reservation> all = findByHostId(reservation.getHost());

        reservation.setId(generateNextId(reservation.getHost()));
        all.add(reservation);
        writeAll(all, reservation.getHost().getId());

        return reservation;
    }

    @Override
    public boolean update(Reservation reservation) throws DataException {
        List<Reservation> all = findByHostId(reservation.getHost());
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getId() == reservation.getId()) {
                all.set(i, reservation);
                writeAll(all, reservation.getHost().getId());
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteById(int id, Host host) throws DataException {
        List<Reservation> all = findByHostId(host);
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getId() == id) {
                all.remove(i);
                writeAll(all, host.getId());
                return true;
            }
        }
        return false;
    }

    public int generateNextId(Host host) throws DataException {
        List<Reservation> all = findByHostId(host);
        if (all.isEmpty()) return 1;
        return all.get(all.size() - 1).getId() + 1;
    }

    private void writeAll(List<Reservation> reservations, String fileName) throws DataException {
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(Paths.get(directory, fileName + ".csv")))) {
            writer.println(HEADER);

            for (Reservation reservation : reservations) {
                writer.println(serialize(reservation));
            }
        } catch (IOException ex) {
            throw new DataException(ex);
        }
    }

    private String serialize(Reservation reservation) {
        StringBuilder builder = new StringBuilder(100);
        builder.append(reservation.getId()).append(DELIMITER)
                .append(reservation.getStart_date()).append(DELIMITER)
                .append(reservation.getEnd_date()).append(DELIMITER)
                .append(reservation.getGuestId()).append(DELIMITER)
                .append(reservation.getTotal());
        return builder.toString();
    }

    private Reservation deserialize(String line) throws DataException {
        String[] fields = line.split(DELIMITER);

        if (fields.length != 5) {
            return null;
        }

        Reservation result = new Reservation();
        result.setId(Integer.parseInt(fields[0]));
        result.setStart_date(LocalDate.parse(fields[1]));
        result.setEnd_date(LocalDate.parse(fields[2]));
        result.setGuestId(Integer.parseInt(fields[3]));

        Guest guest = guestRepository.findById(result.getGuestId());
        if (guest != null) {
            result.setGuest(guest);
        }

        result.setTotal(BigDecimal.valueOf(Double.parseDouble(fields[4])));
        return result;
    }
}
