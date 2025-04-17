package learn.mastery.domain;

import learn.mastery.data.DataException;
import learn.mastery.data.GuestRepository;
import learn.mastery.data.HostRepository;
import learn.mastery.data.ReservationRepository;
import learn.mastery.models.Guest;
import learn.mastery.models.Host;
import learn.mastery.models.Reservation;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final GuestRepository guestRepository;
    private final HostRepository hostRepository;

    public ReservationService(ReservationRepository repository, GuestRepository guestRepository, HostRepository hostRepository) {
        this.reservationRepository = repository;
        this.guestRepository = guestRepository;
        this.hostRepository = hostRepository;
    }

    public Reservation findById(int id) throws DataException {
        return reservationRepository.findById(id);
    }

    public List<Reservation> findByHostId(Host host) throws DataException {
        return reservationRepository.findByHostId(host);
    }

    public Result addReservation(Reservation reservation) throws DataException {
        Result result = validate(reservation);

        if (!result.isSuccess()) {
            return result;
        }

        if (!LocalDate.now().isBefore(reservation.getStart_date())) {
            result.addMessages("Start Date must be in the future.");
            return result;
        }

        result.setReservation(reservationRepository.create(reservation));

        return result;
    }

    public Result updateReservation(Reservation reservation) throws DataException {
        Result result = validate(reservation);
        if (!result.isSuccess()) {
            return result;
        }

        result.setReservation(reservationRepository.create(reservation));

        return result;
    }

    public Result removeReservationById(int reservation_id) throws DataException {
        Result result = new Result();

        if (reservationRepository.findById(reservation_id).getStart_date().isBefore(LocalDate.now())) {
            result.addMessages("You cannot delete a past reservation.");
        }

        if (!reservationRepository.deleteById(reservation_id)) {
            result.addMessages(String.format("Reservation with id: %s does not exist.", reservation_id));
        }

        return result;
    }

    public Result validate(Reservation reservation) throws DataException {
        Result result = new Result();

        if (reservation == null) {
            result.addMessages("Reservation cannot be null.");
            return result;
        }

        //need guest, host and start and end dates
        if (reservation.getGuestId() <= 0) {
            result.addMessages("Guest Id is required.");
            return result;
        }

        if (reservation.getHost() == null) {
            result.addMessages("Host is required.");
            return result;
        }

        if (reservation.getStart_date() == null) {
            result.addMessages("Start Date is required.");
            return result;
        }

        if (reservation.getEnd_date() == null) {
            result.addMessages("End Date is required.");
            return result;
        }

        //guest and host must already exist
        boolean hasGuest = false;
        for (Guest guest : guestRepository.findAll()) {
            if (guest.getGuest_id() == reservation.getGuestId()) {
                hasGuest = true;
                break;
            }
        }
        if (!hasGuest) {
            result.addMessages("Guest is required.");
            return result;
        }

        boolean hasHost = false;
        for (Host host : hostRepository.findAll()) {
            if (host.getId().equals(reservation.getHost().getId())) {
                hasHost = true;
                break;
            }
        }
        if (!hasHost) {
            result.addMessages("Host is required.");
            return result;
        }

        //start date must come before end date
        if (reservation.getStart_date().isAfter(reservation.getEnd_date())) {
            result.addMessages("Start Date must be before End Date.");
            return result;
        }

        //reservation may not overlap existing dates
        for (Reservation res : reservationRepository.findAll()) {
            if (reservation.getStart_date().isBefore(res.getStart_date()) && reservation.getEnd_date().isAfter(res.getEnd_date())) {
                result.addMessages("Reservation may not overlap with existing reservation.");
            }
        }

        return result;
    }
}
