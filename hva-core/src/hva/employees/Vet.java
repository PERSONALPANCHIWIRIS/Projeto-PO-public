package hva.employees;

import hva.Hotel;
import hva.animal.Animal;
import hva.animal.Species;
import hva.calculator.Calculator;
import hva.habitat.Habitat;
import hva.calculator.Calculable;


import java.util.Map;
import java.util.TreeMap;
import java.io.Serial;
import java.io.Serializable;

//Classe que representa os veterinarios. Sub-classe da classe Employee
public class Vet extends Employee implements Serializable, Calculable{
    @Serial
    private static final long serialVersionUID = 202410062217L;
    //Mapa dos animais responsabilidade do veterinario
    private Map<String, Species> _species = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    private String work = "VET";

    //Construtor utilizado pelo metodo importFile
    public Vet(Hotel hotel, String name, String key, String[] specieKeys){
        super(hotel, name, key);
        for (String specieKey : specieKeys) {
            Species a = hotel.getSpecie(specieKey); 
            if (a != null) {
                _species.put(specieKey, a);
            }
        }
    }

    //Metodo de apresentañçao da classe Vet
    @Override
    public String toString() {
        return work + "|" + super.toString() + ((_species.size() >= 1) ? "|" + String.join(",", _species.keySet()) : "");
    }

    public String getWork() {
        return work;
    }

    public void addResponsibility(Species specie){
        if (_species.containsKey(specie.getKey())) {
            return;
        }
        _species.put(specie.getKey(), specie);
    }

    public void removeResponsibility(Species specie){
        _species.remove(specie.getKey());
    }

    public Species getSpecie(String key){
        return _species.get(key);
    }

    public double accept(Calculator visitor) {
        return visitor.calculateVet(this);
    }

    public double WorkLoad(){
        double workload = 0;
        for (Species specie : _species.values()) {
            workload += (super.getHotel().getAnimalPopulation(specie)/super.getHotel().getNumberofVets(specie));
        }
        return workload;
    }

}
