package hva.app.animal;

import hva.Hotel;
import hva.app.exceptions.UnknownAnimalKeyException;
import hva.exceptions.AnimalDoNotExistException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;


class DoShowSatisfactionOfAnimal extends Command<Hotel> {

    DoShowSatisfactionOfAnimal(Hotel receiver) {
        super(Label.SHOW_SATISFACTION_OF_ANIMAL, receiver);
    }

    @Override
    protected final void execute() throws CommandException {
        try{
        String animalKey = Form.requestString(Prompt.animalKey());
        double animalSatisfaction = _receiver.animalSatisfaction(animalKey);
        _display.popup((int) animalSatisfaction);
        }
    
        catch (AnimalDoNotExistException e){
            throw new UnknownAnimalKeyException(e.getKey());
        }
    }
}
