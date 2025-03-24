package hva.calculator;

import hva.animal.Animal;
import hva.employees.Handler;
import hva.employees.Vet;

public interface Calculator {
    public double calculateAnimal(Animal animal);
    public double calculateHandler(Handler handler);
    public double calculateVet(Vet vet);
}
