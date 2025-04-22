package learn.mastery.domain;

import learn.mastery.data.DataException;
import learn.mastery.data.GuestRepositoryDouble;
import learn.mastery.data.HostRepositoryDouble;
import learn.mastery.data.ReservationRepositoryDouble;
import learn.mastery.models.Reservation;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static learn.mastery.data.HostRepositoryDouble.HOST;
import static org.junit.jupiter.api.Assertions.*;

class ReservationServiceTest {

    ReservationService service = new ReservationService(new ReservationRepositoryDouble(), new GuestRepositoryDouble(), new HostRepositoryDouble());

    @Test
    void shouldFindById() throws DataException {
        Reservation result = service.findById(1, HOST);
        assertNotNull(result);
        assertEquals(1, result.getGuestId());
    }

    @Test
    void shouldFindByHostId() throws DataException {
        List<Reservation> result = service.findByHostId(HOST);
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void shouldAddReservation() throws DataException {
        Reservation actual = new Reservation(2, LocalDate.of(2026, 2, 1),
                LocalDate.of(2026, 2, 5), 1, new BigDecimal("350"));
        actual.setHost(HOST);
        Result result = service.addReservation(actual);
        assertTrue(result.isSuccess());
        assertEquals(0, result.getErrorMessages().size());
    }

    @Test
    void shouldNotAddNullReservation() throws DataException {
        Result result = service.addReservation(null);
        assertFalse(result.isSuccess());
        assertEquals("Reservation cannot be null.", result.getErrorMessages().get(0));
    }

    @Test
    void shouldNotAddReservationWithGuestIdLessThanEqualToZero() throws DataException {
        Reservation actual = new Reservation(2, LocalDate.of(2026, 2, 1),
                LocalDate.of(2026, 2, 5), 0, new BigDecimal("350"));
        actual.setHost(HOST);
        Result result = service.addReservation(actual);
        assertFalse(result.isSuccess());
        assertEquals("Guest Id is required.", result.getErrorMessages().get(0));

        actual = new Reservation(2, LocalDate.of(2026, 2, 1),
                LocalDate.of(2026, 2, 5), -1, new BigDecimal("350"));
        actual.setHost(HOST);
        Result negResult = service.addReservation(actual);
        assertFalse(negResult.isSuccess());
        assertEquals("Guest Id is required.", negResult.getErrorMessages().get(0));
    }

    @Test
    void shouldNotAddReservationWithoutStartDate() throws DataException {
        Reservation actual = new Reservation(2, null,
                LocalDate.of(2026, 2, 5), 1, new BigDecimal("350"));
        actual.setHost(HOST);
        Result result = service.addReservation(actual);
        assertFalse(result.isSuccess());
        assertEquals("Start Date is required.", result.getErrorMessages().get(0));
    }

    @Test
    void shouldNotAddReservationWithoutEndDate() throws DataException {
        Reservation actual = new Reservation(2, LocalDate.of(2026, 2, 1),
                null, 1, new BigDecimal("350"));
        actual.setHost(HOST);
        Result result = service.addReservation(actual);
        assertFalse(result.isSuccess());
        assertEquals("End Date is required.", result.getErrorMessages().get(0));
    }

    @Test
    void shouldNotAddReservationWithNonExistingHost() throws DataException {
        Reservation actual = new Reservation(2, LocalDate.of(2026, 2, 1),
                LocalDate.of(2026, 2, 5), 1, new BigDecimal("350"));
        actual.setHost(HOST);
        Result result = service.addReservation(actual);
    }

    @Test
    void shouldNotAddReservationWithNonExistingGuest() {

    }

    @Test
    void shouldNotAddReservationWithStartDateNotBeforeEndDate() {

    }

    @Test
    void shouldNotAddReservationWithOverlappingDates() {

    }

    @Test
    void shouldNotAddReservationWithPastStartDate() {

    }

    @Test
    void shouldUpdateReservation() {
    }

    @Test
    void shouldNotUpdateNonExistentReservation() {

    }

    @Test
    void shouldNotUpdateReservationWithoutGuest() {

    }

    @Test
    void shouldNotUpdateReservationWithoutStartDate() {

    }

    @Test
    void shouldNotUpdateReservationWithoutEndDate() {

    }

    @Test
    void shouldNotUpdateReservationWithNonExistingHost() {

    }

    @Test
    void shouldNotUpdateReservationWithNonExistingGuest() {

    }

    @Test
    void shouldNotUpdateReservationWithStartDateNotBeforeEndDate() {

    }

    @Test
    void shouldNotUpdateReservationWithOverlappingDates() {

    }

    @Test
    void shouldRemoveReservationById() {
    }

    @Test
    void shouldNotRemovePastReservation() {

    }
}