package hva.animal;

import hva.Hotel;
import java.io.Serial;
import java.io.Serializable;

//classe que representa o conceito de espécie
public class Species implements Serializable {
    @Serial
    private static final long serialVersionUID = 202410062200L;
    private String _name;
    private String _species_key;
    //referencia ao hotel ao qual a especie pertence
    private Hotel _hotel;

    public Species(Hotel hotel, String name, String key){
        _hotel = hotel;
        _name = name;
        _species_key = key;
    }

    public String getKey(){
        return _species_key;
    }

    public String getName(){
        return _name;
    }
}
