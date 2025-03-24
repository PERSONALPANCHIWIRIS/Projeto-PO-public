package hva.seasons;

import java.io.Serial;
import java.io.Serializable;

public class Fall extends SeasonState implements Serializable{
    @Serial
    private static final long serialVersionUID = 201020241256L;
    public Fall(Season context) {
        super(context, "COMFOLHAS", "LARGARFOLHAS", 1, 5);
    }

    @Override
    public void advance() {
        super.addSeason();
        super.getSeason().setCurrentSeason(new Winter(super.getSeason()));
    }
    
    @Override
    public String toString(){
        return "Fall";
    }
}
