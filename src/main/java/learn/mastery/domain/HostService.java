package learn.mastery.domain;

import learn.mastery.data.DataException;
import learn.mastery.data.HostRepository;
import learn.mastery.data.ReservationRepository;
import learn.mastery.models.Host;
import learn.mastery.models.Reservation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HostService {

    private final HostRepository repository;

    public HostService(HostRepository repository) {
        this.repository = repository;
    }

    public List<Host> findAll() throws DataException {
        return repository.findAll();
    }

    public Host findByEmail(String email) throws DataException {
        return repository.findByEmail(email);
    }
}
