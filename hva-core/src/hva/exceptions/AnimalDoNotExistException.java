package hva.exceptions;


public class AnimalDoNotExistException extends Exception {
    private static final long serialVersionUID = 211020240624L;
    private final String _key;

    public AnimalDoNotExistException(String key) {
        _key = key;
    }

    public String getKey() {
        return _key;
    }
    
}
