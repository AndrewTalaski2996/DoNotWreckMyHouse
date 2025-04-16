package learn.mastery.domain;

import learn.mastery.data.DataException;
import learn.mastery.data.HostRepositoryDouble;
import learn.mastery.data.ReservationRepositoryDouble;
import learn.mastery.models.Host;
import learn.mastery.models.Reservation;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HostServiceTest {

    HostService service = new HostService(new HostRepositoryDouble(), new ReservationRepositoryDouble());

    @Test
    void shouldFindById() throws DataException {
        Host host = service.findById("test_id");
        assertNotNull(host);
        assertEquals("Talaski", host.getLast_name());
        assertEquals("TC", host.getState());
        assertEquals(new BigDecimal(350), host.getStandard_rate());
    }

    @Test
    void shouldNotFindById() throws DataException {
        Host host = service.findById("12984jdi1kwd1");
        assertNull(host);
    }

    @Test
    void shouldFindHostWithReservations() throws DataException {
        Host host = service.findById("test_id");
        List<Reservation> reservations = new ReservationRepositoryDouble().findByHostId(host);
        assertNotNull(host);
        assertNotNull(reservations);

        host = service.findHostWithReservations("test_id");
        assertNotNull(host);
        assertEquals(reservations.get(0), host.getReservations().get(0));
    }
}