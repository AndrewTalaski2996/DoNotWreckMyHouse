package learn.mastery.data;

import learn.mastery.models.Guest;
import learn.mastery.models.Host;
import learn.mastery.models.Reservation;

import java.util.List;

public interface ReservationRepository {

    public List<Reservation> findAll() throws DataException;

    public Reservation findById(int id) throws DataException;

    public List<Reservation> findByHostId(Host host) throws DataException;

    public Reservation create(Reservation reservation) throws DataException;

    public boolean update(Reservation reservation) throws DataException;

    public boolean deleteById(int id) throws DataException;

    public int generateNextId() throws DataException;
}
