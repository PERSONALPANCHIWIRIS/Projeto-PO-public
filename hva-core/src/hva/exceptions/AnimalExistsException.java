package hva.exceptions;

public class AnimalExistsException extends Exception {
    private static final long serialVersionUID = 211020240425L;

    private String _key;

    public AnimalExistsException(String key) {
        _key = key;
    }

    public String getKey() {
        return _key;
    }
    
}
