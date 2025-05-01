package learn.mastery.models;

import java.util.Objects;

public class Guest {

    private int guest_id;
    private String first_name;
    private String last_name;
    private String email;
    private String phone_number;
    private String state;

    public Guest(int guest_id, String first_name, String last_name, String email, String phone_number, String state) {
        this.guest_id = guest_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.phone_number = phone_number;
        this.state = state;
    }

    public Guest() {}

    public int getGuest_id() {
        return guest_id;
    }

    public void setGuest_id(int guest_id) {
        this.guest_id = guest_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Guest guest = (Guest) o;
        return guest_id == guest.guest_id && Objects.equals(first_name, guest.first_name) && Objects.equals(last_name, guest.last_name) && Objects.equals(email, guest.email) && Objects.equals(phone_number, guest.phone_number) && Objects.equals(state, guest.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guest_id, first_name, last_name, email, phone_number, state);
    }
}
