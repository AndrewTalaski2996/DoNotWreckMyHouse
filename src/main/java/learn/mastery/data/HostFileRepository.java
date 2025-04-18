package learn.mastery.data;

import learn.mastery.models.Host;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository
public class HostFileRepository implements HostRepository{

    private static final String DELIMITER = ",";
    private static final String DELIMITER_REPLACEMENT = "@@@";
    private final String directory;

    public HostFileRepository(@Value("${hostRepository}") String directory) {
        this.directory = directory;
    }

    @Override
    public List<Host> findAll() throws DataException {
        ArrayList<Host> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(directory))) {
            reader.readLine(); //skip header
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                result.add(deserialize(line));
            }
        } catch (IOException ex) {
            //don't throw on read
        }
        return result;
    }

    @Override
    public Host findByEmail(String email) throws DataException {
        return findAll().stream()
                .filter(h -> h.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    private String serialize(Host host) {
        StringBuffer buffer = new StringBuffer(100);
        buffer.append(host.getId()).append(DELIMITER);
        buffer.append(clean(host.getLast_name())).append(DELIMITER);
        buffer.append(clean(host.getEmail())).append(DELIMITER);
        buffer.append(clean(host.getPhone_number())).append(DELIMITER);
        buffer.append(clean(host.getStreet_address())).append(DELIMITER);
        buffer.append(clean(host.getCity())).append(DELIMITER);
        buffer.append(clean(host.getState())).append(DELIMITER);
        buffer.append(clean(host.getZip_code())).append(DELIMITER);
        buffer.append(host.getStandard_rate()).append(DELIMITER);
        buffer.append(host.getWeekend_rate()).append(DELIMITER);
        return buffer.toString();
    }

    private Host deserialize(String line) {
        String[] fields = line.split(DELIMITER);
        if (fields.length != 10) {
            return null;
        }
        Host result = new Host();
        result.setId(restore(fields[0]));
        result.setLast_name(restore(fields[1]));
        result.setEmail(restore(fields[2]));
        result.setPhone_number(restore(fields[3]));
        result.setStreet_address(restore(fields[4]));
        result.setCity(restore(fields[5]));
        result.setState(restore(fields[6]));
        result.setZip_code(restore(fields[7]));
        result.setStandard_rate(BigDecimal.valueOf(Double.parseDouble(fields[8])));
        result.setWeekend_rate(BigDecimal.valueOf(Double.parseDouble(fields[9])));
        return result;
    }

    private String clean(String value) {
        return value.replace(DELIMITER, DELIMITER_REPLACEMENT);
    }

    private String restore(String value) {
        return value.replace(DELIMITER_REPLACEMENT, DELIMITER);
    }
}
