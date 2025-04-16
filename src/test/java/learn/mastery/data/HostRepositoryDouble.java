package learn.mastery.data;

import learn.mastery.models.Host;
import learn.mastery.models.Reservation;

import java.util.ArrayList;
import java.util.List;

public class HostRepositoryDouble implements HostRepository {

    public final static Host HOST = new Host();
    private final ArrayList<Host> hosts = new ArrayList<>();

    public HostRepositoryDouble() { hosts.add(HOST); }

    @Override
    public List<Host> findAll() throws DataException {
        return new ArrayList<>(hosts);
    }

    @Override
    public Host findById(String id) throws DataException {
        return findAll().stream()
                .filter(h -> h.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
