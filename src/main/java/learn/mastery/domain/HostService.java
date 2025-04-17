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
    private final ReservationRepository reservationRepository;

    public HostService(HostRepository repository, ReservationRepository reservationRepository) {
        this.repository = repository;
        this.reservationRepository = reservationRepository;
    }

    public Host findByEmail(String email) throws DataException {
        return repository.findByEmail(email);
    }

    public Host findHostWithReservations(String email) throws DataException {
        Host host = repository.findByEmail(email);
        if (host != null) {
            List<Reservation> reservations = reservationRepository.findByHostId(host);
            host.setReservations(reservations);
        }
        return host;
    }
}
