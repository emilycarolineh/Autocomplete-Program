//Emily Higgs
//UserHistoryException

public class UserHistoryException extends RuntimeException {
    public UserHistoryException() {
        this(null);
    }

    public UserHistoryException(String message) {
        super(message);
    }
}
