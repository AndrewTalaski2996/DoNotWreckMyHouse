package learn.mastery.data;

import learn.mastery.models.Host;
import learn.mastery.models.Reservation;

import java.util.List;

public interface HostRepository {

    public List<Host> findAll() throws DataException;

    public Host findByEmail(String email) throws DataException;
}
