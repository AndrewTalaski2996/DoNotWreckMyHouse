package learn.mastery.ui;

import learn.mastery.data.DataException;
import learn.mastery.domain.GuestService;
import learn.mastery.domain.HostService;
import learn.mastery.domain.ReservationService;
import learn.mastery.domain.Result;
import learn.mastery.models.Guest;
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
        view.displayHeader("View Reservations By Host");
        String hostEmail = view.getHostByEmail();
        Host host = hostService.findHostWithReservations(hostEmail);
        view.displayHostAndReservations(host);
        view.enterToContinue();
    }

    private void makeReservation_Controller() throws DataException {
        view.displayHeader("Make a Reservation");
        Host host = view.chooseHost(hostService.findAll()); //choose host
        Guest guest = view.chooseGuest(guestService.findAll()); //choose guest
        Reservation reservation = view.makeReservation_View(host, guest);
        Result result = reservationService.addReservation(reservation);
        if (!result.isSuccess()) {
            view.displayStatus(false, result.getErrorMessages());
        } else {
            view.displayStatus(true, "Reservation created.");
        }
        view.enterToContinue();
    }

    //RE-EXAMINE LOGIC HERE
    private void editReservation_Controller() throws DataException {
        view.displayHeader("Change a Reservation");

        Host host = view.chooseHost(hostService.findAll());
        Guest guest = view.chooseGuest(guestService.findAll());
        Reservation reservation = view.findReservation(host, guest, reservationService.findAll());
        reservation = view.editReservation_View(reservation);

        Result result = reservationService.updateReservation(reservation);
        if (!result.isSuccess()) {
            view.displayStatus(false, result.getErrorMessages());
        } else {
            view.displayStatus(true, "Reservation updated.");
        }
        view.enterToContinue();
    }

    private void cancelReservation_Controller() {
        view.displayHeader("Cancel Reservation");
        //find reservation
            //getHost and getGuest by id/email
        //view.displayHost and reservation
        //null/not found check
            //result = service.remove reservation
            //view.displayStatus
    }
}
