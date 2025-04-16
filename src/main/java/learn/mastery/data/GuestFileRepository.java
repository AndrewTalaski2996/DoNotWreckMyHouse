package learn.mastery.data;

import learn.mastery.models.Guest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GuestFileRepository implements GuestRepository{

    private static final String HEADER = "id,first_name,last_name,email,phone_number,state";
    private static final String DELIMITER = ",";
    private static final String DELIMITER_REPLACEMENT = "@@@";
    private final String directory;

    public GuestFileRepository(@Value("${guestRepository}") String directory) {
        this.directory = directory;
    }

    @Override
    public List<Guest> findAll() throws DataException {
        ArrayList<Guest> result = new ArrayList<>();
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
    public Guest findById(int id) throws DataException {
        return findAll().stream()
                .filter(g -> g.getGuest_id() == id)
                .findFirst()
                .orElse(null);
    }

    private String serialize(Guest guest) {
        StringBuffer buffer = new StringBuffer(100);
        buffer.append(guest.getGuest_id()).append(DELIMITER);
        buffer.append(clean(guest.getFirst_name())).append(DELIMITER);
        buffer.append(clean(guest.getLast_name())).append(DELIMITER);
        buffer.append(clean(guest.getEmail())).append(DELIMITER);
        buffer.append(clean(guest.getPhone_number())).append(DELIMITER);
        buffer.append(clean(guest.getState())).append(DELIMITER);
        return buffer.toString();
    }

    private Guest deserialize(String line) {
        String[] fields = line.split(DELIMITER);

        if (fields.length != 6) {
            return null;
        }

        Guest result = new Guest();
        result.setGuest_id(Integer.parseInt(fields[0]));
        result.setFirst_name(restore(fields[1]));
        result.setLast_name(restore(fields[2]));
        result.setEmail(restore(fields[3]));
        result.setPhone_number(restore(fields[4]));
        result.setState(restore(fields[5]));
        return result;
    }

    private String clean(String value) {
        return value.replace(DELIMITER, DELIMITER_REPLACEMENT);
    }

    private String restore(String value) {
        return value.replace(DELIMITER_REPLACEMENT, DELIMITER);
    }
}
