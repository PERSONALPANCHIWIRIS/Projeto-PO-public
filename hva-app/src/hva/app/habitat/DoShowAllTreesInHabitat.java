package hva.app.habitat;

import hva.Hotel;
import hva.app.exceptions.DuplicateHabitatKeyException;
import hva.app.exceptions.UnknownHabitatKeyException;
import hva.exceptions.HabitatDoNotExistException;
import hva.habitat.Habitat;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME import other classes if needed

class DoShowAllTreesInHabitat extends Command<Hotel> {

    DoShowAllTreesInHabitat(Hotel receiver) {
        super(Label.SHOW_TREES_IN_HABITAT, receiver);
        //FIXME add command fields if needed
    }

    @Override
    protected void execute() throws CommandException {
        try {
            String habitat_key;
            habitat_key = Form.requestString(Prompt.habitatKey());
            _display.popup(_receiver.getHabitat(habitat_key).getTreeCollection());
        } catch (HabitatDoNotExistException e) {
            throw new UnknownHabitatKeyException(e.getKey());
        }
        
    }

}
