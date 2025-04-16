package learn.mastery.data;

import learn.mastery.models.Host;
import learn.mastery.models.Reservation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservationRepositoryDouble implements ReservationRepository {

    public final static Reservation RESERVATION = new Reservation(1, LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 5), 1, new BigDecimal("100"));
    private final ArrayList<Reservation> reservations = new ArrayList<>();

    public ReservationRepositoryDouble() { reservations.add(RESERVATION); }

    @Override
    public List<Reservation> findAll() throws DataException {
        return new ArrayList<>(reservations);
    }

    @Override
    public Reservation findById(int id) throws DataException {
        return findAll().stream()
                .filter(r -> r.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Reservation> findByHostId(Host host) throws DataException {
        return new ArrayList<>(reservations);
    }

    @Override
    public Reservation create(Reservation reservation) throws DataException {
        List<Reservation> all = findAll();
        all.add(reservation);
        return reservation;
    }

    @Override
    public boolean update(Reservation reservation) throws DataException {
        List<Reservation> all = findAll();

        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getId() == reservation.getId()) {
                all.set(i, reservation);
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
                return true;
            }
        }

        return false;
    }
}
