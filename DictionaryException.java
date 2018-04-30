//Emily Higgs
//DictionaryException

public class DictionaryException extends RuntimeException {

	public DictionaryException() {
        this(null);
    }

    public DictionaryException(String message) {
        super(message);
    }
}
