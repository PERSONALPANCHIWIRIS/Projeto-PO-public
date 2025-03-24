package hva.exceptions;

public class EmployeeExistsException extends Exception {
    private static final long serialVersionUID = 202423102106L;
    private String _key;

    public EmployeeExistsException(String key) {
        _key = key;
    }

    public String getKey() {
        return _key;
    }
    
}
