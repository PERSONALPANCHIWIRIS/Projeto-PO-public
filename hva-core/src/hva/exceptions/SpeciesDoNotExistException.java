package hva.exceptions;

public class SpeciesDoNotExistException extends Exception {
    private static final long serialVersionUID = 1L;
    private String _key;

    public SpeciesDoNotExistException(String key) {
        _key = key;
    }

    public String getKey() {
        return _key;
    }
    
}
