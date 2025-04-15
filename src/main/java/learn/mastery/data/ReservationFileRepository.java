package learn.mastery.data;

import learn.mastery.models.Reservation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReservationFileRepository implements ReservationRepository{

    private static final String HEADER = "id,start_date,end_date,guest,total";
    private static final String DELIMITER = ",";
    private final String directory;

    public ReservationFileRepository(@Value("${reservationRepository}") String directory) {
        this.directory = directory;
    }

    @Override
    public List<Reservation> findAll() throws DataException {
        ArrayList<Reservation> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(directory))) {
            reader.readLine(); //skip header

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                result.add(deserialize(line));
            }
        } catch (IOException ex) {
            //don't throw on read
        }
        return result;
    }

    @Override
    public Reservation findById(int id) throws DataException {
        return findAll().stream()
                .filter(r -> r.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Reservation create(Reservation reservation) throws DataException {

        if (reservation == null) {
            return null;
        }

        List<Reservation> all = findAll();

        all.add(reservation);
        writeAll(all);

        return reservation;
    }

    @Override
    public boolean update(Reservation reservation) throws DataException {
        List<Reservation> all = findAll();
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getId() == reservation.getId()) {
                all.set(i, reservation);
                writeAll(all);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteById(int id) throws DataException {
        List<Reservation> all = findAll();
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getId() == id) {
                all.remove(i);
                writeAll(all);
                return true;
            }
        }
        return false;
    }

    private void writeAll(List<Reservation> reservations) throws DataException {
        try (PrintWriter writer = new PrintWriter(directory)) {
            writer.println(HEADER);

            for (Reservation reservation : reservations) {
                writer.println(serialize(reservation));
            }
        } catch (FileNotFoundException ex) {
            throw new DataException(ex);
        }
    }

    private String serialize(Reservation reservation) {
        StringBuffer buffer = new StringBuffer(100);
        buffer.append(reservation.getId()).append(DELIMITER);
        buffer.append(reservation.getStart_date()).append(DELIMITER);
        buffer.append(reservation.getEnd_date()).append(DELIMITER);
        buffer.append(reservation.getGuestId()).append(DELIMITER);
        buffer.append(reservation.getTotal()).append(DELIMITER);
        return buffer.toString();
    }

    private Reservation deserialize(String line) {
        String[] fields = line.split(DELIMITER);

        if (fields.length != 5) {
            return null;
        }

        Reservation result = new Reservation();
        result.setId(Integer.parseInt(fields[0]));
        result.setStart_date(LocalDate.parse(fields[1]));
        result.setEnd_date(LocalDate.parse(fields[2]));
        result.setGuestId(Integer.parseInt(fields[3]));
        result.setTotal(BigDecimal.valueOf(Long.parseLong(fields[4])));
        return result;
    }
}
