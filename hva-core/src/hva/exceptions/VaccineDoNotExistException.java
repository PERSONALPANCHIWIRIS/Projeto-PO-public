package hva.exceptions;

public class VaccineDoNotExistException extends Exception {
    private static final long serialVersionUID = 130519451235L;

    private String _key;

    public VaccineDoNotExistException(String key) {
        _key = key;
    }

    public String getKey() {
        return _key;
    }
}
