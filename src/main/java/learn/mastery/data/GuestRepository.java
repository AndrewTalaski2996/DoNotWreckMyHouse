package learn.mastery.data;

import learn.mastery.models.Guest;

import java.util.List;

public interface GuestRepository {

    public List<Guest> findAll() throws DataException;

    public Guest findById(int id) throws DataException;
}
