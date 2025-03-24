package hva.app.search;

import hva.Hotel;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import hva.exceptions.HabitatDoNotExistException;
import hva.app.exceptions.UnknownHabitatKeyException;


class DoShowAnimalsInHabitat extends Command<Hotel> {

    DoShowAnimalsInHabitat(Hotel receiver) {
        super(Label.ANIMALS_IN_HABITAT, receiver);
    }

    @Override
    protected void execute() throws CommandException {
        try{
            String habitat_key = Form.requestString(hva.app.habitat.Prompt.habitatKey());
            _display.popup(_receiver.getHabitat(habitat_key).showOwnAnimals());
        }
        catch (HabitatDoNotExistException e){
            throw new UnknownHabitatKeyException(e.getKey());
        }
    }

}
