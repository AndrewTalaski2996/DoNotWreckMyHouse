package learn.mastery.domain;

import learn.mastery.models.Reservation;

import java.util.ArrayList;
import java.util.List;

public class Result {

    private ArrayList<String> messages = new ArrayList<>();

    private Reservation reservation;

    public Reservation getReservation() { return reservation; }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public boolean isSuccess() { return messages.isEmpty(); }

    public List<String> getErrorMessages() { return new ArrayList<>(messages); }

    public void addMessages(String message) { messages.add(message); }
}
