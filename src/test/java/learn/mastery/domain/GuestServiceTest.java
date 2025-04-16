package learn.mastery.domain;

import learn.mastery.data.DataException;
import learn.mastery.data.GuestRepositoryDouble;
import learn.mastery.models.Guest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GuestServiceTest {

    GuestService service = new GuestService(new GuestRepositoryDouble());

    @Test
    void shouldFindById() throws DataException {
        Guest guest = service.findById(1);
        assertNotNull(guest);
        assertEquals("Andrew", guest.getFirst_name());
        assertEquals("Talaski", guest.getLast_name());
        assertEquals("email@gmail.com", guest.getEmail());
        assertEquals("(988) 988 9880", guest.getPhone_number());
        assertEquals("NC", guest.getState());
    }

    @Test
    void shouldNotFindById() throws DataException {
        Guest guest = service.findById(999);
        assertNull(guest);
    }
}