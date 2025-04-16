package learn.mastery.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Host {

    private String id;
    private String last_name;
    private String email;
    private String phone_number;
    private String street_address;
    private String city;
    private String state;
    private String zip_code;
    private List<Reservation> reservations = new ArrayList<>();
    private BigDecimal standard_rate;
    private BigDecimal weekend_rate;

    public Host(String id, String last_name, String email, String phone_number, String street_address, String city, String state, String zip_code,
                BigDecimal standard_rate, BigDecimal weekend_rate) {
        this.id = id;
        this.last_name = last_name;
        this.email = email;
        this.phone_number = phone_number;
        this.street_address = street_address;
        this.city = city;
        this.state = state;
        this.zip_code = zip_code;
        this.standard_rate = standard_rate;
        this.weekend_rate = weekend_rate;
    }

    public Host() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getStreet_address() {
        return street_address;
    }

    public void setStreet_address(String street_address) {
        this.street_address = street_address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip_code() {
        return zip_code;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public BigDecimal getStandard_rate() {
        return standard_rate;
    }

    public void setStandard_rate(BigDecimal standard_rate) {
        this.standard_rate = standard_rate;
    }

    public BigDecimal getWeekend_rate() {
        return weekend_rate;
    }

    public void setWeekend_rate(BigDecimal weekend_rate) {
        this.weekend_rate = weekend_rate;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Host host = (Host) o;
        return Objects.equals(id, host.id) && Objects.equals(last_name, host.last_name) && Objects.equals(email, host.email) && Objects.equals(phone_number, host.phone_number) && Objects.equals(street_address, host.street_address) && Objects.equals(city, host.city) && Objects.equals(state, host.state) && Objects.equals(zip_code, host.zip_code) && Objects.equals(reservations, host.reservations) && Objects.equals(standard_rate, host.standard_rate) && Objects.equals(weekend_rate, host.weekend_rate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, last_name, email, phone_number, street_address, city, state, zip_code, reservations, standard_rate, weekend_rate);
    }
}
