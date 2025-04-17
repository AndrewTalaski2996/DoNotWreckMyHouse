package learn.mastery.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Reservation {

    private int id;
    private LocalDate start_date;
    private LocalDate end_date;
    private int guest_id;
    private Host host;
    private BigDecimal total;

    public Reservation(int id, LocalDate start_date, LocalDate end_date, int guest_id, BigDecimal total) {
        this.id = id;
        this.start_date = start_date;
        this.end_date = end_date;
        this.guest_id = guest_id;
        this.total = total;
    }

    public Reservation() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDate start_date) {
        this.start_date = start_date;
    }

    public LocalDate getEnd_date() {
        return end_date;
    }

    public void setEnd_date(LocalDate end_date) {
        this.end_date = end_date;
    }

    public int getGuestId() {
        return guest_id;
    }

    public void setGuestId(int guest_id) {
        this.guest_id = guest_id;
    }

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return id == that.id && Objects.equals(start_date, that.start_date) && Objects.equals(end_date, that.end_date) && guest_id == that.guest_id && Objects.equals(total, that.total);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, start_date, end_date, guest_id, total);
    }
}
