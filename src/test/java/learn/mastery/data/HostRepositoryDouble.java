package learn.mastery.data;

import learn.mastery.models.Host;
import learn.mastery.models.Reservation;

import java.util.List;

public class HostRepositoryDouble implements HostRepository {
    @Override
    public List<Host> findAll() throws DataException {
        return List.of();
    }

    @Override
    public Host findById(String id) throws DataException {
        return null;
    }

    @Override
    public List<Reservation> findReservations(Host host) throws DataException {
        return List.of();
    }
}
