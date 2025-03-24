package hva.exceptions;

public class HabitatExistsException extends Exception {
    private static final long serialVersionUID = 202410200137L;
    
    private final String habitat_key;

  /** @param key */
  public HabitatExistsException(String key) {
    habitat_key = key;
  }

  /** @return the key */
  public String getKey() {
    return habitat_key;
  }
}
