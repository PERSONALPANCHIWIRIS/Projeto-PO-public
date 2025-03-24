package hva.animal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import hva.exceptions.WrongVaccineException;

import hva.habitat.Habitat;
import hva.vaccine.Vaccine;
import hva.calculator.Calculable;
import hva.calculator.Calculator;

import java.io.Serial;
import java.io.Serializable;

//Classe que representa o conceito de animal
public class Animal implements Serializable, Calculable {
    @Serial
    private static final long serialVersionUID = 202410062158L;
    private Species _specie; //referencia a especie do animal
    private String animal_key;
    private String _name;
    //Uma referencia do habitat associado ao animal
    private Habitat _habitat;
    private List<String> health_history = new ArrayList<>(); //uma lista de strings que representa
    //a historia de saude (vacinações) do animal

    public Animal(Species specie, String key, String name, Habitat habitat){
        _specie = specie;
        animal_key = key;
        _name = name;
        _habitat = habitat;
        //Como o animal ainda não foi vacinado, inicializa-se a historia de saude
        //como "VOID"
        health_history.add("VOID");
    }

    //retorna o id do animal
    public String getKey(){
        return animal_key;
    }

    //retorna o habitat do animal
    public Habitat getHabitat(){
        return _habitat;
    }

    //Metodo toString para a apresentação do animal
    @Override
    public String toString(){
        String health = (health_history.size() >= 1 && health_history.get(0) != "VOID") ? String.join(",", health_history)
        : "VOID";
        return "ANIMAL|" + animal_key + "|" + _name + "|" + _specie.getKey() + "|" +
         health + "|" + _habitat.getKey();
    }

    public Species getSpecie() {
        return _specie;
    }

    public double accept(Calculator visitor) {
        return visitor.calculateAnimal(this);
    }

    public void setHabitat(Habitat habitat) {
        _habitat = habitat;
    }

    public List<String> getMedicalActs() {
        if (health_history.size() == 1 && health_history.get(0).equals("VOID")) {
            return new ArrayList<>();
        }
        return health_history;
    }

    public boolean vaccinate(Vaccine vaccine) throws WrongVaccineException {
        boolean wronged = false;
        if (health_history.size() == 1 && health_history.get(0).equals("VOID")) {
            health_history.clear();
        }
        if (vaccine.getSpecies().containsKey(_specie.getKey())) {
            health_history.add("NORMAL");
            vaccine.addApllication();
            return wronged; 
        }
        int MaxDamage = 0;
        for (Species specie : vaccine.getSpecies().values()) {
            int damage = sizeNames(specie, _specie) - commonCharacters(specie, _specie); 
            MaxDamage = Math.max(MaxDamage, damage);
        }
        vaccine.addApllication();
        if (MaxDamage == 0) {
            health_history.add("CONFUSÃO");
        } else if (MaxDamage >= 1 && MaxDamage <= 4) {
            health_history.add("ACIDENTE");
        } else if (MaxDamage >= 5) {
            health_history.add("ERRO");
        }
        return !wronged;
    }

    public int sizeNames(Species s1, Species s2) {
        return Math.max(s1.getName().length(), s2.getName().length());
    }

    public int commonCharacters(Species s1, Species s2) {
        Set<Character> char1 = new TreeSet<>();
        Set<Character> char2 = new TreeSet<>();

        for (char c : s1.getName().toCharArray()) {
            char1.add(Character.toLowerCase(c));
        }
        for (char c : s2.getName().toCharArray()) {
            char2.add(Character.toLowerCase(c));
        }

        char1.retainAll(char2);
        return char1.size();
    }

}
