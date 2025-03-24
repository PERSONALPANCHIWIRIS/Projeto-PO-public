package hva.habitat;

import java.util.Map;
import java.util.TreeMap;
import hva.Hotel;
import hva.animal.Species;
import hva.calculator.SatisfactionCalculator;
import hva.animal.Animal;
import hva.habitat.Tree;
import hva.vaccine.Vaccine;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Collection;
import java.util.Collections;

import hva.exceptions.SpeciesDoNotExistException;
import hva.exceptions.TreeExistsException;
import hva.exceptions.AnimalExistsException;

//Classe que representa o conceito de Habitat
public class Habitat implements Serializable {
    @Serial
    private static final long serialVersionUID = 202410062142L;
    //referencia ao hotel
    private Hotel _hotel;
    private String habitat_key;
    private String _name;
    private int _area;
    //Um mapa que contem para cada chave (ID de cada espécie) o valor
    //de adequação para este habitat
    private Map<String, Integer> _adjustements = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    //Mapa das arvores no habitat
    private Map<String, Tree> _trees = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    //Mapa dos animais
    private Map<String, Animal> _animals = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    //Mapa das expecies associadas ao habitat
    private Map<String, Species> _species = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    //Este construtor é utilizado pelo importFile
    public Habitat(Hotel hotel, String key, String name, int area, String[] treeKeys) {
        _hotel = hotel;
        habitat_key = key;
        _name = name;
        _area = area;
        for (String treeKey : treeKeys) {
            Tree tree = hotel.getTree(treeKey); 
            if (tree != null) {
                _trees.put(treeKey, tree);
            }
        }
    }

    public void registerTree(String tree_key, String treeName,
     int treeAge, double treeDifficulty, String treeType) throws TreeExistsException {
        if (_trees.containsKey(tree_key)) {
            throw new TreeExistsException(tree_key);
        }
        int season_planted = Integer.parseInt(_hotel.seasonAdvanced());
        switch(treeType){
            case "PERENE":
                _trees.put(tree_key, new Perene(this, tree_key, treeName, treeAge, treeDifficulty, season_planted));
                break;
            case "CADUCA":
                _trees.put(tree_key, new Caduca(this, tree_key, treeName, treeAge, treeDifficulty, season_planted));
                break;
        }
    }

    public String getKey(){
        return habitat_key;
    }

    //Função para registar um unico animal
    public void registerAnimal(Animal animal) throws AnimalExistsException{
        if (_animals.containsKey(animal.getKey())) {
            throw new AnimalExistsException(animal.getKey());
        }
        _animals.put(animal.getKey(), animal);
    }

    public Animal getAnimal(String key){
        return _animals.get(key);
    }

    //Função que cria o String acumulado de apresentação
    //de todos os aniamis no habitat
    public Collection<String> showOwnAnimals(){
        List<String> All_List = new ArrayList<>();
        List<Animal> animalsList = new ArrayList<>(_animals.values());
        
        animalsList.sort((v1, v2) -> v1.getKey().compareToIgnoreCase(v2.getKey()));
        
        for (Animal animal : animalsList) {
            All_List.add(animal.toString());
        }
        
        return Collections.unmodifiableCollection(All_List);
    }

    //Função de apresentação do habitat
    @Override
    public String toString(){
        //Cria a primeira linha, que apresenta os dados do habitat
        String firstLine = "HABITAT" + "|" + habitat_key + "|" + _name + "|" + _area + "|" + _trees.size();
        return firstLine;
    }

    public Map<String,Tree> getTrees(){
        return _trees;
    }

    public Map<String,Animal> getAnimals(){
        return _animals;
    }

    public void setArea(int area){
        _area = area;
    }

    public int getArea(){
        return _area;
    }

    public int getPopulation(){
        return _animals.size();
    }

    public int sameSpecie(Species specie, Animal example){
        int count = 0;
        for (Animal animal : _animals.values()) {
            if (animal.getSpecie().equals(specie) && !animal.equals(example)) {
                count++;
            }
        }
        return count;
    }

    public int differentSpecie(Species specie){
        int count = 0;
        for (Animal animal : _animals.values()) {
            if (!animal.getSpecie().equals(specie)) {
                count++;
            }
        }
        return count;
    }

    public void setAdjustement(String speciesKey, String influence) throws SpeciesDoNotExistException {
        if (_hotel.getSpecie(speciesKey) == null) {
            throw new SpeciesDoNotExistException(speciesKey);
        }
        int adjustement = switch (influence) {
            case "POS" -> 20;
            case "NEG" -> -20;
            case "NEU" -> 0;
            default -> 0; // Placeholder
        };
        _adjustements.put(speciesKey, adjustement);
    }

    public int getAdjustement(Species specie){
        //Caso a espécie não tenha ajustamento, retorna 0
        if (!_adjustements.containsKey(specie.getKey())) {
            return 0;
        }
        return _adjustements.get(specie.getKey());
    }

    public Collection<String> getTreeCollection(){
        List<String> trees = new ArrayList<>();
        for (Tree tree : _trees.values()) {
            trees.add(tree.toString());
        }
        return trees;
    }

    public Hotel getHotel(){
        return _hotel;
    }

    public Tree getTree(String key){
        return _trees.get(key);
    }

    public double satisfaction(SatisfactionCalculator calculator){
        double satisfaction = 0;
        for (Animal animal : _animals.values()) {
            satisfaction += animal.accept(calculator);
        }
        return satisfaction;
    }

    public void removeAnimal(Animal animal){
        _animals.remove(animal.getKey());
    }

    public double workInHabitat(){
        double tree_work = 0;
        for (Tree tree : _trees.values()) {
            tree_work += tree.cleanEffort();
        }
        double total_work = _area + 3*getPopulation() + tree_work;
        return total_work;
    }

}
