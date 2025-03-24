package hva.vaccine;

import hva.Hotel;
import java.util.Map;
import java.util.TreeMap;

import hva.animal.Species;
import java.io.Serial;
import java.io.Serializable;

//Classe que representa o conceito de vacinas
public class Vaccine implements Serializable {
    @Serial
    private static final long serialVersionUID = 202410062138L;
    private String _name;
    private String _key;
    //referencia ao hotel
    private Hotel _hotel;
    //Mapa das especies às que a vacina pode ser aplicada
    private Map<String, Species> _species = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    private int _applications;

    //Construtor utilizado pelo metodo importFile
    public Vaccine(Hotel hotel, String name, String key, String[] speciesKeys){
        _hotel = hotel;
        _name = name;
        _key = key;
        _applications = 0;
        for (String speciesKey : speciesKeys) {
            Species s = hotel.getSpecies(speciesKey);
            if (s != null) {
                _species.put(speciesKey, s);
            }
        }
    }   

    //metodo toString utilizado para a apresentação da vacina
    @Override
    public String toString(){
        return "VACINA" + "|" + _key + "|" + _name + "|" + _applications + ((_species.size() >= 1) ?
         ("|" + String.join(",", _species.keySet())) : "");
    }

    public String getKey(){
        return _key;
    }   

    public Map<String, Species> getSpecies(){
        return _species;
    }

    public void addApllication(){
        _applications++;
    }

}
