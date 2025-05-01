package learn.mastery.data;

import learn.mastery.models.Host;
import learn.mastery.models.Reservation;

import java.util.List;

public interface ReservationRepository {

    public List<Reservation> findAll() throws DataException;

    public Reservation findById(int id, Host host) throws DataException;

    public List<Reservation> findByHostId(Host host) throws DataException;

    public Reservation create(Reservation reservation) throws DataException;

    public boolean update(Reservation reservation) throws DataException;

    public boolean deleteById(int id, Host host) throws DataException;

    public int generateNextId(Host host) throws DataException;
}
