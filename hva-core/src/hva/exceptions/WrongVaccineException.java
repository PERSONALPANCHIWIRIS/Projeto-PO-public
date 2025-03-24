package hva.exceptions;

public class WrongVaccineException extends Exception {
    private static final long serialVersionUID = 241020240413L;
    private String _vaccine_key;
    private String _animal_key;

    public WrongVaccineException(String Vaccine_key, String Animal_key) {
        _vaccine_key = Vaccine_key;
        _animal_key = Animal_key;
    }

    public String getVaccineKey() {
        return _vaccine_key;
    }

    public String getAnimalKey() {
        return _animal_key;
    }
}
