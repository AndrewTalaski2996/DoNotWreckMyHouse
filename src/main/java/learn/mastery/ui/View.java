package learn.mastery.ui;

import learn.mastery.domain.Result;
import learn.mastery.models.Guest;
import learn.mastery.models.Host;
import learn.mastery.models.Reservation;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

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

    public Reservation makeReservation_View(Host host, Guest guest) {
        Reservation reservation = new Reservation();
        reservation.setStart_date(io.readLocalDate("Start Date [MM/dd/yyyy]: "));
        reservation.setEnd_date(io.readLocalDate("End Date [MM/dd/yyyy]: "));
        reservation.setGuestId(guest.getGuest_id());
        reservation.setGuest(guest);
        reservation.setHost(host);
        reservation.setTotal(reservation.calculateTotal());
        return reservation;
    }

    public Host chooseHost(List<Host> hosts) {
        if (hosts.isEmpty()) {
            io.println("No Hosts found.");
            return null;
        }

        String host_email = io.readRequiredString("Enter a Host's email: ");
        return hosts.stream()
                .filter(h -> h.getEmail().equals(host_email))
                .findFirst()
                .orElse(null);
    }

    public Guest chooseGuest(List<Guest> guests) {
        if (guests.isEmpty()) {
            io.println("No Guests found.");
            return null;
        }

        String guest_email = io.readRequiredString("Enter the guest's email: ");
        return guests.stream()
                .filter(g -> g.getEmail().equals(guest_email))
                .findFirst()
                .orElse(null);
    }

    public Reservation editReservation_View(Reservation reservation) {
        displayText("Editing");
        String startDate = io.readRequiredString("Start Date: ");
        if (!startDate.isBlank()) {
            reservation.setStart_date(LocalDate.parse(startDate));
        }
        String endDate = io.readRequiredString("End Date: ");
        if (!endDate.isBlank()) {
            reservation.setEnd_date(LocalDate.parse(endDate));
        }
        return reservation;
    }

    public Reservation findReservation(Host host, Guest guest, List<Reservation> reservations) {
        return reservations.stream()
                .filter(r -> r.getHost().getEmail().equals(host.getEmail()) && r.getGuest().getEmail().equals(guest.getEmail()))
                .findFirst()
                .orElse(null);
    }

    public Result cancelReservation_View() {
        return null;
    }

    public String getHostByEmail() {
        return null;
    }

    public void displayHeader(String message) {
        io.println("");
        io.println(message);
        io.println("#".repeat(message.length()));
    }

    public void enterToContinue() { io.readString("Press [Enter] to continue."); }

    public void displayText(String line) {
        System.out.println();
        System.out.println(line);
    }

    public void displayStatus(boolean success, String message) {
        displayStatus(success, List.of(message));
    }

    public void displayStatus(boolean success, List<String> messages) {
        displayHeader(success ? "Success" : "Error");
        for (String message : messages) {
            io.println(message);
        }
    }

    public void displayHostAndReservations(Host host) {
        displayHeader(host.getLast_name() + ": " + host.getCity() + ", " + host.getState());
        for (Reservation res : host.getReservations()) {
            displayText("ID: " + res.getId() + ", " + res.getStart_date() + " - " + res.getEnd_date() +
                    ", Guest: " + res.getGuest().getLast_name() + ", " + res.getGuest().getFirst_name() +
                    " Email: " + res.getGuest().getEmail());
        }
    }
}
