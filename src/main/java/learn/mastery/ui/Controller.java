package learn.mastery.ui;

import learn.mastery.data.DataException;
import learn.mastery.domain.GuestService;
import learn.mastery.domain.HostService;
import learn.mastery.domain.ReservationService;
import learn.mastery.models.Host;
import learn.mastery.models.Reservation;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Controller {

    private final GuestService guestService;
    private final HostService hostService;
    private final ReservationService reservationService;
    private final View view;

    public Controller(GuestService guestService, HostService hostService, ReservationService reservationService, View view) {
        this.guestService = guestService;
        this.hostService = hostService;
        this.reservationService = reservationService;
        this.view = view;
    }

    public void run() {
        view.displayHeader("Welcome to Don't Wreck My House.");

        try {
            runAppLoop();
        } catch (DataException ex) {
            //something went wrong
        }

        view.displayHeader("Goodbye!");
    }

    private void runAppLoop() throws DataException {
        boolean exit = false;

        while (!exit) {
            int selection = view.selectMainMenuOption();

            switch (selection) {
                case 0:
                    exit = true;
                    break;
                case 1:
                    showReservations_Controller();
                    break;
                case 2:
                    makeReservation_Controller();
                    break;
                case 3:
                    editReservation_Controller();
                    break;
                case 4:
                    cancelReservation_Controller();
                    break;
                default:
                    view.displayText("Something went wrong.");
            }
        }
    }

    private void showReservations_Controller() throws DataException {
        String hostId = view.getHost();
        Host host = hostService.findById(hostId); //or email??
        //view.displayHost and their reservations
        //entertocontinue
    }

    private void makeReservation_Controller() {
        //header
        //make new reservation
            //need to have a readLocalDate
        //result = reservation service.add reservation
        //view.displayStatus
    }

    private void editReservation_Controller() {
        //header
        //find reservation
            //getHost and getGuest by id/email
        //view.displayhost and reservations
        //null/not found check
            //result = service.update reservation
            //view.displayStatus
    }

    private void cancelReservation_Controller() {
        //header
        //find reservation
            //getHost and getGuest by id/email
        //view.displayHost and reservation
        //null/not found check
            //result = service.remove reservation
            //view.displayStatus
    }
}
