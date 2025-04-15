package learn.mastery.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static org.junit.jupiter.api.Assertions.*;

class ReservationFileRepositoryTest {

    static final String SEED_PATH = "./data/reservation-seed.csv";
    static final String TEST_PATH = "./data/reservations_test/9d469342-ad0b-4f5a-8d28-e81e690ba29a.csv";
    static final String TEST_DIR = "./data/reservations_test";

    ReservationFileRepository repository = new ReservationFileRepository(TEST_DIR);

    @BeforeEach
    void setup() throws IOException {
        Path seedPath = Paths.get(SEED_PATH);
        Path testPath = Paths.get(TEST_PATH);
        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void shouldFindAll() {
    }

    @Test
    void shouldFindById() {
    }

    @Test
    void shouldCreate() {
    }

    @Test
    void shouldUpdate() {
    }

    @Test
    void shouldDeleteById() {
    }
}