package learn.mastery.ui;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleIO {

    private final Scanner console = new Scanner(System.in);

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
            int result = Integer.parseInt(readRequiredString(prompt));
            if (result >= 0 && result <= 4) {
                return result;
            }
            println("That is not a valid option.");
        }
    }

    //readInt

    //readLocalDate

    //readBigDecimal
}
