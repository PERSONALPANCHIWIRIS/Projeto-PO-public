package hva.exceptions;

public class TreeExistsException extends Exception {
    private static final long serialVersionUID = 202110291622L;
    private String _key;

    public TreeExistsException(String key){
        _key = key;
    }

    public String getKey(){
        return _key;
    }
    
}
