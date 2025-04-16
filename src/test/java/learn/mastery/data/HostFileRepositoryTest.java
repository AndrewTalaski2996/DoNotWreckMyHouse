package learn.mastery.data;

import learn.mastery.models.Host;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static org.junit.jupiter.api.Assertions.*;

class HostFileRepositoryTest {

    static final String SEED_PATH = "./data/hosts-seed.csv";
    static final String TEST_PATH = "./data/hosts-test.csv";

    HostFileRepository repository = new HostFileRepository(TEST_PATH);

    @BeforeEach
    void setup() throws IOException {
        Path seedPath = Paths.get(SEED_PATH);
        Path testPath = Paths.get(TEST_PATH);
        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void shouldFindAll() throws DataException {
        assertEquals(9, repository.findAll().size());
    }

    @Test
    void shouldFindById() throws DataException {
        Host valasek = repository.findById("d491d4c3-e005-4494-9c52-4d3be265fd76");
        assertNotNull(valasek);
        assertEquals("Valasek", valasek.getLast_name());
        assertEquals("hvalasek5@fastcompany.com", valasek.getEmail());
        assertEquals("(713) 3421887", valasek.getPhone_number());
        assertEquals("8113 Lunder Crossing", valasek.getStreet_address());
        assertEquals("Houston", valasek.getCity());
        assertEquals("TX", valasek.getState());
        assertEquals("77005", valasek.getZip_code());
        assertEquals(new BigDecimal("387.0"), valasek.getStandard_rate());
        assertEquals(new BigDecimal("483.75"), valasek.getWeekend_rate());
    }
}