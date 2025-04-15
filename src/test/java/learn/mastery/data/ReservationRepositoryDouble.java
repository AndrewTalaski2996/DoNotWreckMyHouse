package learn.mastery.data;

import learn.mastery.models.Reservation;

import java.util.List;

public class ReservationRepositoryDouble implements ReservationRepository {
    @Override
    public List<Reservation> findAll() throws DataException {
        return List.of();
    }

    @Override
    public Reservation findById(int id) throws DataException {
        return null;
    }

    @Override
    public Reservation create(Reservation reservation) throws DataException {
        return null;
    }

    @Override
    public boolean update(Reservation reservation) throws DataException {
        return false;
    }

    @Override
    public boolean deleteById(int id) throws DataException {
        return false;
    }
}
