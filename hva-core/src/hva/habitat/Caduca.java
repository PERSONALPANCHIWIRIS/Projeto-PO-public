package hva.habitat;
import java.io.Serial;
import java.io.Serializable;
import hva.Hotel;

//Classe da arvore de tipo caduca, sub-casse de Tree
public class Caduca extends Tree implements Serializable {
    @Serial
    private static final long serialVersionUID = 202410062256L;
    private String _type = "CADUCA";
    //A string byocicle representa o ciclo biologico
    //que depende da estação
    private String _Biocycle;

    public Caduca(Hotel hotel, String key, String name, int age, double difficulty, int season_planted) {
        super(hotel, key, name, age, difficulty, season_planted);
        //Assume-se que no inicio o programa se encontra
        //na estação primavera
        _Biocycle = super.getHotel().getCurrentSeason().getBiocycle(_type);
    }

    public Caduca(Habitat habitat, String key, String name, int age, double difficulty, int season_planted) {
        super(habitat, key, name, age, difficulty, season_planted);
        _Biocycle = super.getHabitat().getHotel().getCurrentSeason().getBiocycle(_type);
    }

    //Complementa o metodo toString da classe Tree
    @Override
    public String toString(){
        return super.toString() + _type + "|" + _Biocycle;
    }

    public void updateBiocycle() {
        //quando a arvore foi adicionada pelo comando DoAddTree
        if (super.getHabitat() != null){
            _Biocycle = super.getHabitat().getHotel().getCurrentSeason().getBiocycle(_type);
            int season_number = super.getHabitat().getHotel().getCurrentSeason().getSeasonNumber();
            if (season_number % 4 == super.getSeasonPlanted()){
               super.setAge(super.getAge() + 1);
            }
        }    
        else{
            //quando a arvore foi adicionada pelo importFile
            _Biocycle = super.getHotel().getCurrentSeason().getBiocycle(_type);
            int season_number = super.getHabitat().getHotel().getCurrentSeason().getSeasonNumber();
            if (season_number % 4 == super.getSeasonPlanted()){
               super.setAge(super.getAge() + 1);
            }
        }
    }

    public String getType(){
        return _type;
    }
}
