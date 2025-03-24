package hva.employees;

import hva.Hotel;
import java.io.Serial;
import java.io.Serializable;
import hva.calculator.Calculable;

//Classe que representa todos os empregados. A mesma é abstrata de forma a não ser 
//possível instanciar um empregado sem ser um dos dois tipos definidos(Tratador e
//veterianrio)
public abstract class Employee implements Serializable, Calculable {
    @Serial
    private static final long serialVersionUID = 202410062145L;
    //Referencia ao hotel 
    private Hotel _hotel;
    private String _name;
    private String _employee_key;

    public Employee(Hotel hotel, String name, String key){
        _hotel = hotel;
        _name = name;
        _employee_key = key;
    }

    //Metodo toString do empregado, que sera utilizado pelas sub-classes
    @Override
    public String toString(){
        return _employee_key + "|" + _name;
    }

    public String getKey(){
        return _employee_key;
    }

    public abstract String getWork();

    public Hotel getHotel(){
        return _hotel;
    }
}
