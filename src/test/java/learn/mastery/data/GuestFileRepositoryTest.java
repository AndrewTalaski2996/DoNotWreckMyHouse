package learn.mastery.data;

import learn.mastery.models.Guest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static org.junit.jupiter.api.Assertions.*;

class GuestFileRepositoryTest {

    static final String SEED_FILE_PATH = "./data/guests-seed.csv";
    static final String TEST_FILE_PATH = "./data/guests-test.csv";

    GuestFileRepository repository = new GuestFileRepository(TEST_FILE_PATH);

    @BeforeEach
    void setup() throws IOException {
        Path seedPath = Paths.get(SEED_FILE_PATH);
        Path testPath = Paths.get(TEST_FILE_PATH);
        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void shouldFindAll() throws DataException {
        assertEquals(10, repository.findAll().size());
    }

    @Test
    void shouldFindById() throws DataException {
        Guest gecks = repository.findById(2);
        assertNotNull(gecks);
        assertEquals("Olympie", gecks.getFirst_name());
        assertEquals("ogecks1@dagondesign.com", gecks.getEmail());
        assertEquals("(202) 2528316", gecks.getPhone_number());
        assertEquals("DC", gecks.getState());
    }
}