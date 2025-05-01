package learn.mastery.ui;

import learn.mastery.data.DataException;
import learn.mastery.domain.HostService;
import learn.mastery.domain.ReservationService;
import learn.mastery.domain.Result;
import learn.mastery.models.Guest;
import learn.mastery.models.Host;
import learn.mastery.models.Reservation;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

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

    public Host chooseHost(HostService service) throws DataException {
        List<Host> hosts = service.findAll();
        if (hosts.isEmpty()) {
            io.println("No Hosts found.");
            return null;
        }

        String host_email = io.readRequiredString("Enter a Host's email: ");
        Host host = service.findByEmail(host_email);

        if (host == null) {
            displayStatus(false, "No such Host found.");
        }

        return host;
    }

    public Guest chooseGuest(List<Guest> guests) {
        if (guests.isEmpty()) {
            io.println("No Guests found.");
            return null;
        }

        String guest_email = io.readRequiredString("Enter the guest's email: ");
        Guest guest = guests.stream()
                .filter(g -> g.getEmail().equals(guest_email))
                .findFirst()
                .orElse(null);

        if (guest == null) {
            displayStatus(false, "No such guest found.");
        }

        return guest;
    }

    public Reservation editReservation_View(Host host, Guest guest, List<Reservation> reservations) {
        List<Reservation> toEdit = findReservation(host, guest, reservations);

        displayReservation(host, toEdit);

        Reservation reserve = io.readId("Which reservation would you like to edit? (By ID): ", toEdit);

        displayText("Editing Reservation " + reserve.getId());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        String startDate = io.readRequiredString("Start Date: ");
        if (!startDate.isBlank()) {
            reserve.setStart_date(LocalDate.parse(startDate, formatter));
        }

        String endDate = io.readRequiredString("End Date: ");
        if (!endDate.isBlank()) {
            reserve.setEnd_date(LocalDate.parse(endDate, formatter));
        }

        return reserve;
    }

    public Reservation removeReservation_View(Host host, Guest guest, List<Reservation> reservations) {
        List<Reservation> toRemove = findReservation(host, guest, reservations);

        displayReservation(host, toRemove);

        Reservation reserve = io.readId("Which reservation would you like to delete? (By ID): ", toRemove);

        displayText("Deleting Reservation " + reserve.getId());

        return reserve;
    }

    private List<Reservation> findReservation(Host host, Guest guest, List<Reservation> reservations) {
        return reservations.stream()
                .filter(r -> r.getHost().getEmail().equals(host.getEmail()) && r.getGuest().getEmail().equals(guest.getEmail()))
                .collect(Collectors.toList());
    }

    public String getHostByEmail() {
        return io.readRequiredString("Enter the Host's email: ");
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

    public Result confirmUserChoice_View(String prompt) {
        Result result = new Result();
        if (!io.confirm(prompt)) {
            result.addMessages("Declined.");
        }
        return result;
    }

    public void displayHostAndReservations(Host host, ReservationService service) throws DataException {
        if (host == null) {
            displayText("No such Host found.");
            return;
        } else if (service.findByHostId(host) == null || service.findByHostId(host).isEmpty()) {
            displayText("No reservations found for host.");
            return;
        }
        displayHeader(host.getLast_name() + ": " + host.getCity() + ", " + host.getState());
        for (Reservation res : service.findByHostId(host)) {
            displayText("ID: " + res.getId() + ", " + res.getStart_date() + " - " + res.getEnd_date() +
                    ", Guest: " + res.getGuest().getLast_name() + ", " + res.getGuest().getFirst_name() +
                    " Email: " + res.getGuest().getEmail());
        }
    }

    public void displayReservation(Host host, List<Reservation> reservations) {
        if (reservations == null || reservations.isEmpty()) {
            displayText("No reservations found.");
            return;
        }
        displayHeader(host.getLast_name() + ": " + host.getCity() + ", " + host.getState());
        for (Reservation r : reservations) {
            displayText("ID: " + r.getId() + ", " + r.getStart_date() + " - " + r.getEnd_date() +
                    ", Guest: " + r.getGuest().getLast_name() + ", " + r.getGuest().getFirst_name() +
                    " Email: " + r.getGuest().getEmail());
        }
    }
}
