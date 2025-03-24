package hva.seasons;

import java.io.Serial;
import java.io.Serializable;

public class Winter extends SeasonState implements Serializable {
    @Serial
    private static final long serialVersionUID = 211020240309L;
    public Winter(Season context) {
        super(context, "LARGARFOLHAS", "SEMFOLHAS", 2, 0);
    }

    @Override
    public void advance() {
        super.addSeason();
        super.getSeason().setCurrentSeason(new Spring(super.getSeason()));
    }
    
    @Override
    public String toString(){
        return "Winter";
    }
}
