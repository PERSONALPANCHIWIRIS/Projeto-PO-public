package hva.employees;

import hva.Hotel;
import hva.calculator.Calculator;
import hva.exceptions.HabitatDoNotExistException;
import hva.habitat.Habitat;
import hva.habitat.Tree;

import java.util.Map;
import java.util.TreeMap;
import java.io.Serial;
import java.io.Serializable;


//Classe que representa os tratadores, sub-classe de Employee
public class Handler extends Employee implements Serializable {
    @Serial
    private static final long serialVersionUID = 202410062150L;
    //Cada tratador tem um conjunto de habitats, as responsabilidades
    //do mesmo
    private Map<String, Habitat> _habitats = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    //String indicadora do tipo de empregado
    private String work = "TRT";

    //Este construtor ´s utilizado pelo metodo importFile
    public Handler(Hotel hotel, String name, String key, String[] habitatKeys) throws HabitatDoNotExistException{
        super(hotel, name, key);
        for (String habitatKey : habitatKeys) {
                Habitat h = hotel.getHabitat(habitatKey);
                if (h != null) {
                    _habitats.put(habitatKey, h);
                }
                else {
                    throw new HabitatDoNotExistException(habitatKey);
                }
        }
    }

    //O metodo toString para a apresentação do tratador utiliza o metodo
    //toString da classe "mãe"
    @Override
    public String toString() {
        return work + "|" + super.toString() + ((_habitats.size() >= 1) ? "|" + String.join(",", _habitats.keySet()) : "");
    }

    public String getWork() {
        return work;
    }

    public void addResponsibility(Habitat habitat){
        if (_habitats.containsKey(habitat.getKey())) {
            return;
        }
        _habitats.put(habitat.getKey(), habitat);
    }

    public void removeResponsibility(Habitat habitat){
        _habitats.remove(habitat.getKey());
    }

    public Habitat getHabitat(String key){
        return _habitats.get(key);
    }

    public double accept(Calculator visitor) {
        return visitor.calculateHandler(this);
    }

    public double WorkLoad(){
        double workload = 0;
        for (Habitat habitat : _habitats.values()) {
            double work = (habitat.workInHabitat()/habitat.getHotel().getNumberofHandlers(habitat));
            workload += work;
        }
        return workload;
    }
}
