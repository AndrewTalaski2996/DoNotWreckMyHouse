package learn.mastery.data;

public class DataException extends Exception {
    public DataException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public DataException(Throwable cause) { super(cause); }

    public DataException(String message) {
        super(message);
    }
}
