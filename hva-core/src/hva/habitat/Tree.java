package hva.habitat;

import java.io.Serial;
import java.io.Serializable;

import hva.Hotel;

//Classe abstrata base que representa o conceito de arvore
public abstract class Tree implements Serializable {
    @Serial
    private static final long serialVersionUID = 202410062140L;
    private String _tree_key;
    private String _name;
    private int _age;
    //Dificuldade de limpar a arvore
    private double _difficulty;
    //Referencia ao habitat ao qual a arvore pertence
    private Habitat _habitat;
    private Hotel _hotel;
    private int _season_planted;

    public Tree(Hotel hotel, String key, String name, int age, double difficulty, int season_planted){ 
        _tree_key = key;
        _name = name;
        _age = age;
        _difficulty = difficulty;
        _hotel = hotel;
        _season_planted = season_planted;
    }

    public Tree(Habitat habitat, String key, String name, int age, double difficulty, int season_planted){
        _habitat = habitat;
        _tree_key = key;
        _name = name;
        _age = age;
        _difficulty = difficulty;
        _season_planted = season_planted;
    }

    
    //Metodo de apresentação toString que será utilizado pelas sub-classes
    //de arvore
    @Override
    public String toString(){
        return "ÁRVORE|" + _tree_key + "|" + _name + "|" + _age + "|" + (int) _difficulty + "|";
    }

    public String getKey(){
        return _tree_key;
    }

    public Habitat getHabitat(){
        return _habitat;
    }

    public int getAge(){
        return _age;
    }   

    public void setAge(int age){
        _age = age;
    }

    public String getName(){
        return _name;
    }

    public double getDifficulty(){
        return _difficulty;
    }

    public Hotel getHotel(){
        return _hotel;
    }

    public int getSeasonPlanted(){
        return _season_planted;
    }

    public abstract void updateBiocycle();

    public abstract String getType();

    public double cleanEffort(){
        String type = getType();
        switch(type){
            case "PERENE":
                return _difficulty * _hotel.getCurrentSeason().getSeasonalPereneDifficulty() * Math.log(_age + 1);
            case "CADUCA":
                return _difficulty * _hotel.getCurrentSeason().getSeasonalCaducaDifficulty() * Math.log(_age + 1);
            default:
                return 0.0;
        }
    }
}
