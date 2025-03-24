package hva.seasons;

import java.io.Serial;
import java.io.Serializable;

public class Summer extends SeasonState implements Serializable {
    @Serial
    private static final long serialVersionUID = 211020230000L;
    public Summer(Season context) {
        super(context, "COMFOLHAS", "COMFOLHAS", 1, 2);
    }

    @Override
    public void advance() {
        super.addSeason();
        super.getSeason().setCurrentSeason(new Fall(super.getSeason()));
    }
    
    @Override
    public String toString(){
        return "Summer";
    }
}
