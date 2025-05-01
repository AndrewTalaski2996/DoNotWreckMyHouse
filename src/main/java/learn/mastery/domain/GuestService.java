package learn.mastery.domain;

import learn.mastery.data.DataException;
import learn.mastery.data.GuestRepository;
import learn.mastery.models.Guest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestService {

    private final GuestRepository guestRepository;

    public GuestService(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    public List<Guest> findAll() throws DataException {
        return guestRepository.findAll();
    }

    public Guest findById(int id) throws DataException {
        return guestRepository.findById(id);
    }
}
