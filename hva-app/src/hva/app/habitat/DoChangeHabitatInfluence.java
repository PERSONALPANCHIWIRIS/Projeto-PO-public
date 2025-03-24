package hva.app.habitat;

import hva.Hotel;
import hva.app.exceptions.UnknownHabitatKeyException;
import hva.app.exceptions.UnknownSpeciesKeyException;
import hva.exceptions.HabitatDoNotExistException;
import hva.exceptions.SpeciesDoNotExistException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import pt.tecnico.uilib.forms.Form;

class DoChangeHabitatInfluence extends Command<Hotel> {

    DoChangeHabitatInfluence(Hotel receiver) {
        super(Label.CHANGE_HABITAT_INFLUENCE, receiver);
    }

    @Override
    protected void execute() throws CommandException {
        try {
            String adjustement;
            String habitat_key;
            String species_key;
            habitat_key = Form.requestString(Prompt.habitatKey());
            species_key = Form.requestString(hva.app.animal.Prompt.speciesKey());
            while (true) {
                adjustement = Form.requestString(Prompt.habitatInfluence());
                if (adjustement.equals("POS") || adjustement.equals("NEG") || adjustement.equals("NEU")) {
                    break;
                }
            }
            _receiver.getHabitat(habitat_key).setAdjustement(species_key, adjustement);
        } catch (HabitatDoNotExistException e) {
            throw new UnknownHabitatKeyException(e.getKey());
        }
        catch (SpeciesDoNotExistException e) {
            throw new UnknownSpeciesKeyException(e.getKey());
        }
    }

}
