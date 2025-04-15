package learn.mastery.data;

import learn.mastery.models.Guest;

import java.util.List;

public class GuestRepositoryDouble implements GuestRepository {
    @Override
    public List<Guest> findAll() throws DataException {
        return List.of();
    }

    @Override
    public Guest findById(int id) throws DataException {
        return null;
    }
}
