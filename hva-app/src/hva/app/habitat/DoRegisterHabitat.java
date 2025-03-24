package hva.app.habitat;

import hva.Hotel;
import hva.app.exceptions.DuplicateHabitatKeyException;
import hva.exceptions.HabitatExistsException;
import hva.habitat.Habitat;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import pt.tecnico.uilib.forms.Form;

class DoRegisterHabitat extends Command<Hotel> {

    DoRegisterHabitat(Hotel receiver) {
        super(Label.REGISTER_HABITAT, receiver);
        //FIXME add command fields if needed
    }

    @Override
    protected void execute() throws CommandException {
        try {
            String name;
            int area;
            String habitat_key;
            habitat_key = Form.requestString(Prompt.habitatKey());
            name = Form.requestString(Prompt.habitatName());
            area = Form.requestInteger(Prompt.habitatArea());
            _receiver.registerHabitat(new Habitat(_receiver, habitat_key, name, area, new String[0]));
        } catch (HabitatExistsException e) {
            throw new DuplicateHabitatKeyException(e.getKey());
        }
    }

}
