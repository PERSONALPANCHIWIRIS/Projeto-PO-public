package hva.exceptions;

public class HabitatDoNotExistException extends Exception {
    private static final long serialVersionUID = 201020242109L;
    private String _key;

    public HabitatDoNotExistException(String key) {
        _key = key;
    }

    public String getKey() {
        return _key;
    }
    
}
