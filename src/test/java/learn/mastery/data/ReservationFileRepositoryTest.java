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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservationFileRepositoryTest {

    static final String SEED_PATH = "./data/reservation-seed.csv";
    static final String TEST_DIR = "./data/reservations_test/";
    static final String TEST_FILE = "test_id.csv";

    Host testHost;
    ReservationFileRepository repository;

    @BeforeEach
    void setup() throws IOException {
        Path seedPath = Paths.get(SEED_PATH);
        Path testFilePath = Paths.get(TEST_DIR, TEST_FILE);
        Files.copy(seedPath, testFilePath, StandardCopyOption.REPLACE_EXISTING);

        testHost = new Host();
        testHost.setId("test_id");

        repository = new ReservationFileRepository(TEST_DIR, new GuestRepositoryDouble());
    }

    @Test
    void shouldFindById() throws DataException {
        List<Reservation> result = repository.findByHostId(testHost);
        assertEquals(1, result.size());
        assertEquals(18, result.get(0).getGuestId());
    }

    @Test
    void shouldCreate() throws DataException {
        Reservation res = new Reservation();
        res.setId(2);
        res.setGuestId(10);
        res.setStart_date(LocalDate.of(2025, 6, 1));
        res.setEnd_date(LocalDate.of(2025, 6, 5));
        res.setTotal(new BigDecimal("600.00"));
        res.setHost(testHost);

        repository.create(res);

        List<Reservation> result = repository.findByHostId(testHost);
        assertEquals(2, result.size());
    }

    @Test
    void shouldUpdate() throws DataException {
        List<Reservation> reservations = repository.findByHostId(testHost);
        Reservation existing = reservations.get(0);
        existing.setTotal(new BigDecimal("800.00"));

        boolean updated = repository.update(existing);
        assertTrue(updated);

        List<Reservation> afterUpdate = repository.findByHostId(testHost);
        assertEquals("800.0", afterUpdate.get(0).getTotal().toPlainString());
    }

    @Test
    void shouldDeleteById() throws DataException {
        boolean deleted = repository.deleteById(1, testHost);
        assertTrue(deleted);

        List<Reservation> result = repository.findByHostId(testHost);
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldGenerateNextId() throws Exception {
        int nextId = repository.generateNextId(testHost);
        assertEquals(2, nextId);
    }
}