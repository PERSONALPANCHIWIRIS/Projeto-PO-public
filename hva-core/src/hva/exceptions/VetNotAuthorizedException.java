package hva.exceptions;

public class VetNotAuthorizedException extends Exception {
    private static final long serialVersionUID = 241020240250L;

    private String vet_key;
    private String specie_key;

    public VetNotAuthorizedException(String specie_key, String vet_key) {
        this.vet_key = vet_key;
        this.specie_key = specie_key;
    }

    public String getVetKey() {
        return vet_key;
    }

    public String getSpecieKey() {
        return specie_key;
    }
    
}
