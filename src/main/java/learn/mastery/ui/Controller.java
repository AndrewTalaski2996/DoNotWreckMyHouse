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

        String email = view.getHostByEmail();
        Host host = hostService.findByEmail(email);
        view.displayHostAndReservations(host, reservationService);

        view.enterToContinue();
    }

    private void makeReservation_Controller() throws DataException {
        view.displayHeader("Make a Reservation");

        Guest guest = getGuest();
        if (guest == null) {
            return;
        }

        Host host = getHost();
        if (host == null) {
            return;
        }

        view.displayHostAndReservations(host, reservationService);

        Reservation reservation = view.makeReservation_View(host, guest);

        Result result = reservationService.addReservation(reservation);

        if (!result.isSuccess()) {
            view.displayStatus(false, result.getErrorMessages());
        } else {
            view.displayHeader("Summary");
            view.displayText("Start Date: " + reservation.getStart_date());
            view.displayText("End Date: " + reservation.getEnd_date());
            view.displayText(String.format("Total: $%.2f", reservation.calculateTotal()));

            Result confirm = view.confirmUserChoice_View("Are you sure? [y/n]: ");

            if (!confirm.isSuccess()) {
                view.displayText( "Reservation creation aborted.");
            } else {
                view.displayStatus(true, "Reservation created.");
            }
        }
        view.enterToContinue();
    }

    private void editReservation_Controller() throws DataException {
        view.displayHeader("Change a Reservation");

        Guest guest = getGuest();
        if (guest == null) {
            return;
        }

        Host host = getHost();
        if (host == null) {
            return;
        }

        Reservation reservation = view.editReservation_View(host, guest, reservationService.findByHostId(host));

        Result result = reservationService.updateReservation(reservation);
        if (!result.isSuccess()) {
            view.displayStatus(false, result.getErrorMessages());
        } else {
            view.displayHeader("Summary");
            view.displayText("New Start Date: " + reservation.getStart_date());
            view.displayText("New End Date: " + reservation.getEnd_date());
            view.displayText(String.format("New Total: $%.2f", reservation.calculateTotal()));

            Result confirm = view.confirmUserChoice_View("Are you sure? [y/n]: ");

            if (!confirm.isSuccess()) {
                view.displayText("Reservation edit aborted.");
            } else {
                view.displayStatus(true, "Reservation updated.");
            }
        }
        view.enterToContinue();
    }

    private void cancelReservation_Controller() throws DataException {
        view.displayHeader("Cancel Reservation");

        Guest guest = getGuest();
        if (guest == null) {
            return;
        }

        Host host = getHost();
        if (host == null) {
            return;
        }

        Reservation reservation = view.removeReservation_View(host, guest, reservationService.findByHostId(host));

        Result confirm = view.confirmUserChoice_View("Are you sure? [y/n]: ");

        if (!confirm.isSuccess()) {
            view.displayText("Reservation removal aborted.");
            return;
        }

        Result result = reservationService.removeReservationById(reservation.getId(), host);
        if (!result.isSuccess()) {
            view.displayStatus(false, result.getErrorMessages());
        } else {
            view.displayStatus(true, "Reservation removed.");
        }
        view.enterToContinue();
    }

    private Host getHost() throws DataException {
         return view.chooseHost(hostService);
    }

    private Guest getGuest() throws DataException {
        return view.chooseGuest(guestService.findAll());
    }
}
