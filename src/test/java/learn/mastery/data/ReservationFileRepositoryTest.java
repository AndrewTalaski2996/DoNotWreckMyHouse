package learn.mastery.data;

import learn.mastery.models.Host;
import learn.mastery.models.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ReservationFileRepositoryTest {

    static final String SEED_PATH = "./data/reservation-seed.csv";
    static final String TEST_PATH = "./data/reservations_test/test_id.csv";

    ReservationFileRepository repository = new ReservationFileRepository(TEST_PATH, new GuestRepositoryDouble());

    @BeforeEach
    void setup() throws IOException {
        Path seedPath = Paths.get(SEED_PATH);
        Path testPath = Paths.get(TEST_PATH);
        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void shouldFindAll() throws DataException {
        assertEquals(1, repository.findAll().size());
    }

    //id,start_date,end_date,guest_id,total
    //1,2020-07-01,2020-07-02,18,870

    @Test
    void shouldFindById() throws DataException {
        Host host = new Host();
        Reservation result = repository.findById(1, host);
        assertNotNull(result);
        assertEquals(LocalDate.of(2020,7,1), result.getStart_date());
        assertEquals(LocalDate.of(2020, 7, 2), result.getEnd_date());
        assertEquals(18, result.getGuestId());
        assertEquals(new BigDecimal(870), result.getTotal());
    }

    @Test
    void shouldCreate() throws DataException {
        Reservation result = repository.create(new Reservation(2, LocalDate.of(2020, 8, 2), LocalDate.of(2020, 8, 5), 17, new BigDecimal(890)));
        assertNotNull(result);
        assertEquals(LocalDate.of(2020,8,2), result.getStart_date());
        assertEquals(LocalDate.of(2020, 8, 5), result.getEnd_date());
        assertEquals(17, result.getGuestId());
        assertEquals(new BigDecimal(890), result.getTotal());
    }

    @Test
    void shouldUpdate() throws DataException {
        assertTrue(repository.update(repository.findAll().get(0)));
    }

    @Test
    void shouldDeleteById() throws DataException {
        assertTrue(repository.deleteById(1, HostRepositoryDouble.HOST));
        assertEquals(0, repository.findAll().size());
    }
}