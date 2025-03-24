package hva.seasons;

import java.io.Serial;
import java.io.Serializable;

public class Spring extends SeasonState implements Serializable{

    @Serial
    private static final long serialVersionUID = 211020240212L;
    public Spring(Season context) {
        super(context, "GERARFOLHAS", "GERARFOLHAS", 1, 1);
    }

    @Override
    public void advance() {
        super.addSeason();
        super.getSeason().setCurrentSeason(new Summer(super.getSeason()));
    }

    @Override
    public String toString(){
        return "Spring";
    }
    
}
