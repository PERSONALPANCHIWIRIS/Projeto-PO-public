package hva.exceptions;

public class VaccineExistsException extends Exception {
    private static final long serialVersionUID = 241020240154L;

    private String _key;

    public VaccineExistsException(String key) {
        _key = key;
    }

    public String getKey() {
        return _key;
    }
    
}
