package hva.exceptions;

public class EmployeeDoNotExistException extends Exception {
    private static final long serialVersionUID = 231020240906L;
    private String _key;

    public EmployeeDoNotExistException(String key) {
        _key = key;
    }

    public String getKey() {
        return _key;
    }
    
}
