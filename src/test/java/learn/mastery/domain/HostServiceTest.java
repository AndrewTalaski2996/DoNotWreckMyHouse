package learn.mastery.domain;

import learn.mastery.data.DataException;
import learn.mastery.data.GuestRepositoryDouble;
import learn.mastery.data.HostRepositoryDouble;
import learn.mastery.data.ReservationRepositoryDouble;
import learn.mastery.models.Guest;
import learn.mastery.models.Host;
import learn.mastery.models.Reservation;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HostServiceTest {

    HostService service = new HostService(new HostRepositoryDouble());

    @Test
    void shouldFindById() throws DataException {
        Host host = service.findByEmail("email@gmail.com");
        assertNotNull(host);
        assertEquals("Talaski", host.getLast_name());
        assertEquals("TC", host.getState());
        assertEquals(new BigDecimal(350), host.getStandard_rate());
    }

    @Test
    void shouldNotFindById() throws DataException {
        Host host = service.findByEmail("12984jdi1kwd1");
        assertNull(host);
    }
}