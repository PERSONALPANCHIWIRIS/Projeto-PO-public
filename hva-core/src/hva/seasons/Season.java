package hva.seasons;

import java.io.Serial;
import java.io.Serializable;

public class Season implements Serializable{
    @Serial
    private static final long serialVersionUID = 21102024157L;
    private SeasonState _current_season;
    private int _current_season_n = 0;

    public Season(){
        _current_season = new Spring(this);
    }

    public void advanceSeason(){
        _current_season.advance();
    }

    public void setCurrentSeason(SeasonState season){
        _current_season = season;
    }

    public SeasonState getCurrentSeason(){
        return _current_season;
    }

    public void addSeason(){
        _current_season_n++;
    }

    public int getSeasonNumber(){
        return _current_season_n;
    }
}
