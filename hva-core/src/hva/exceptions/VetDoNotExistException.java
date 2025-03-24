package hva.exceptions;

public class VetDoNotExistException extends Exception {
    private static final long serialVersionUID = 241020240246L;

    private String _key;

    public VetDoNotExistException(String key) {
        _key = key;
    }

    public String getKey() {
        return _key;
    }
    
}
