package learn.mastery.domain;

import java.util.ArrayList;
import java.util.List;

public class Result<T> {

    private T payload;

    public T getPayload() { return payload; }

    public void setPayload(T payload) { this.payload = payload; }

    private ArrayList<String> messages = new ArrayList<>();

    public boolean isSuccess() { return messages.isEmpty(); }

    public List<String> getErrorMessages() { return new ArrayList<>(messages); }

    public void addMessages(String message) { messages.add(message); }
}
