package hva.exceptions;

public class NoResponsibilityElementException extends Exception {
    private static final long serialVersionUID = 181920222108L;
    private String _key;    
    private String _responsibility;

    public NoResponsibilityElementException(String key, String responsibility){
        _key = key;
        _responsibility = responsibility;
    }

    public String getKey(){
        return _key;
    }

    public String getResponsibility(){
        return _responsibility;
    }
}
