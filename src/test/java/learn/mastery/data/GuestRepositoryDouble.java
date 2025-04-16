package learn.mastery.data;

import learn.mastery.models.Guest;

import java.util.ArrayList;
import java.util.List;

public class GuestRepositoryDouble implements GuestRepository {

    public final static Guest GUEST = new Guest(1, "Andrew", "Talaski", "email@gmail.com", "(988) 988 9880", "NC");
    private final ArrayList<Guest> guests = new ArrayList<>();

    public GuestRepositoryDouble() { guests.add(GUEST); }

    @Override
    public List<Guest> findAll() {
        return new ArrayList<>(guests);
    }

    @Override
    public Guest findById(int id) {
        return findAll().stream()
                .filter(g -> g.getGuest_id() == id)
                .findFirst()
                .orElse(null);
    }
}
