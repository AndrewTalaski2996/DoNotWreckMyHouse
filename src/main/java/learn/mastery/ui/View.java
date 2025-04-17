package learn.mastery.ui;

import learn.mastery.models.Host;
import org.springframework.stereotype.Component;

@Component
public class View {

    private final ConsoleIO io;

    public View(ConsoleIO io) { this.io = io; }

    public int selectMainMenuOption() {
        displayHeader("Main Menu");
        displayText("0. Exit\n1. View Reservations by Host\n2. Make a Reservation\n" +
                "3. Update a Reservation\n4. Cancel a Reservation");

        return io.readIntForMenu("Select [0-4]: ");
    }

    //viewReservations

    //makeReservation_View

    //editReservation_View

    //cancelReservation_View

    public String getHost() {
        displayHeader("View reservations for Host");
        return ""; //io.readString
    }

    public void displayHeader(String message) {
        io.println("");
        io.println(message);
        io.println("#".repeat(message.length()));
    }

    //other helper methods

    public void enterToContinue() { io.readString("Press [Enter] to continue."); }

    public void displayText(String line) {
        System.out.println();
        System.out.println(line);
    }

    //displayStatus

    //displayHostAndReservations
}
