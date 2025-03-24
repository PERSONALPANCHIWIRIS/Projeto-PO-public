package hva.seasons;

import java.io.Serial;
import java.io.Serializable;

public abstract class SeasonState implements Serializable {
    @Serial
    private static final long serialVersionUID = 211020240200L;
    private String Perene_Cycle;
    private String Caduca_Cycle;

    private int _seasonal_caduca_difficulty;
    private int _seasonal_perene_difficulty;

    private Season _season;

    public SeasonState(Season season, String perene_cycle, String caduca_cycle,
                       int seasonal_perene_difficulty, int seasonal_caduca_difficulty) {
        _season = season;
        Perene_Cycle = perene_cycle;
        Caduca_Cycle = caduca_cycle;
        _seasonal_perene_difficulty = seasonal_perene_difficulty;
        _seasonal_caduca_difficulty = seasonal_caduca_difficulty;
    }

    public Season getSeason(){
        return _season;
    }

    public abstract void advance();

    public String getBiocycle(String type){
        if (type.equals("PERENE")) {
            return Perene_Cycle;
        } else {
            return Caduca_Cycle;
        }
    }

    public void addSeason(){
        _season.addSeason();
    }

    public int getSeasonNumber(){
       return  _season.getSeasonNumber();
    }

    public int getSeasonalPereneDifficulty(){
        return _seasonal_perene_difficulty;
    }

    public int getSeasonalCaducaDifficulty(){
        return _seasonal_caduca_difficulty;
    }
    
}
