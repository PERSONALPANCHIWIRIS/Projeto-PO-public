package hva.app.search;

import hva.Hotel;
import hva.app.exceptions.UnknownAnimalKeyException;
import hva.exceptions.AnimalDoNotExistException;
import java.util.List;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import pt.tecnico.uilib.forms.Form;


class DoShowMedicalActsOnAnimal extends Command<Hotel> {

    DoShowMedicalActsOnAnimal(Hotel receiver) {
        super(Label.MEDICAL_ACTS_ON_ANIMAL, receiver);
    }

    @Override
    protected void execute() throws CommandException {
        try{
            String animal_key = Form.requestString(hva.app.animal.Prompt.animalKey());
            List<String> medical_acts = _receiver.getMedicalActsOnAnimal(animal_key);
            _display.popup(String.join(",", medical_acts));
        }
        catch(AnimalDoNotExistException e){
            throw new UnknownAnimalKeyException(e.getKey());
        }
        
    }

}
