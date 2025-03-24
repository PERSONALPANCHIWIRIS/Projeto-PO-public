package hva.app.habitat;

import hva.Hotel;
import hva.app.exceptions.UnknownHabitatKeyException;
import hva.exceptions.HabitatDoNotExistException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import pt.tecnico.uilib.forms.Form;


class DoChangeHabitatArea extends Command<Hotel> {

    DoChangeHabitatArea(Hotel receiver) {
        super(Label.CHANGE_HABITAT_AREA, receiver);
    }

    @Override
    protected void execute() throws CommandException {
        try {
            int new_area;
            String habitat_key;
            habitat_key = Form.requestString(Prompt.habitatKey());
            new_area = Form.requestInteger(Prompt.habitatArea());
            _receiver.getHabitat(habitat_key).setArea(new_area);
        } catch (HabitatDoNotExistException e) {
            throw new UnknownHabitatKeyException(e.getKey());
        }
    }

}
