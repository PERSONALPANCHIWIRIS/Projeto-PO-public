package hva.calculator;

import java.io.Serial;
import java.io.Serializable;

import hva.animal.Animal;
import hva.habitat.Habitat;
import hva.employees.Handler;
import hva.employees.Vet;

public class SatisfactionCalculator implements Calculator, Serializable {
    @Serial
    private static final long serialVersionUID = 211020240551L;
    
    @Override
    public double calculateAnimal(Animal animal) {
        Habitat habitat = animal.getHabitat();
        double same = 3 * habitat.sameSpecie(animal.getSpecie(), animal);
        double different = 2 * habitat.differentSpecie(animal.getSpecie());
        double health = habitat.getArea()/(habitat.getPopulation()) + habitat.getAdjustement(animal.getSpecie());
        return 20 + same - different + health;
    }

    @Override
    public double calculateHandler(Handler handler) {
        return 300 - handler.WorkLoad();
    }
    
    @Override
    public double calculateVet(Vet vet) {
        return 20 - vet.WorkLoad();
    }
}
