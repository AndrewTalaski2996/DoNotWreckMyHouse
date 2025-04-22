package learn.mastery.ui;

import learn.mastery.models.Reservation;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

@Component
public class ConsoleIO {

    private final Scanner console = new Scanner(System.in);
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public void print(String message) {
        System.out.print(message);
    }

    public void println(String message) {
        System.out.println(message);
    }

    public String readString(String prompt) {
        print(prompt);
        return console.nextLine();
    }

    public String readRequiredString(String prompt) {
        while (true) {
            String result = readString(prompt);
            if (!result.isBlank()) {
                return result;
            }
            println("Value is required.");
        }
    }

    public int readIntForMenu(String prompt) {
        while (true) {
            try {
                int result = Integer.parseInt(readRequiredString(prompt));
                if (result >= 0 && result <= 4) {
                    return result;
                }
            } catch (NumberFormatException ex) {
                println("That is not a valid option.");
            }
        }
    }

    public Reservation readId(String prompt, List<Reservation> reservations) {
        while (true) {
            try {
                int result = Integer.parseInt(readRequiredString(prompt));
                for (Reservation r : reservations) {
                    if (result == r.getId()) {
                        return r;
                    }
                }
                println("That is not a valid ID.");
            } catch (NumberFormatException ex) {
                println("That is not a valid option.");
            }
        }
    }

    public LocalDate readLocalDate(String prompt) {
        while (true) {
            String input = readRequiredString(prompt);
            try {
                return LocalDate.parse(input, formatter);
            } catch (DateTimeParseException ex) {
                println("This is not a valid date.");
            }
        }
    }

    public boolean confirm(String prompt) {
        while (true) {
            String confirm = readRequiredString(prompt);
            if (confirm.equalsIgnoreCase("y")) {
                return true;
            } else if (confirm.equalsIgnoreCase("n")) {
                return false;
            }
            println("That is not a valid choice.");
        }
    }
}
