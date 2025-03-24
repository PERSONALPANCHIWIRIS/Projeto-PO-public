package hva.app.animal;

import hva.Hotel;

import hva.app.exceptions.DuplicateAnimalKeyException;
import hva.app.exceptions.UnknownAnimalKeyException;
import hva.app.exceptions.UnknownHabitatKeyException;
import hva.exceptions.HabitatDoNotExistException;
import hva.exceptions.AnimalDoNotExistException;
import hva.exceptions.AnimalExistsException;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;


class DoTransferToHabitat extends Command<Hotel> {

    DoTransferToHabitat(Hotel hotel) {
        super(Label.TRANSFER_ANIMAL_TO_HABITAT, hotel);

    }

    @Override
    protected final void execute() throws CommandException {
        try{
            String animal_key = Form.requestString(Prompt.animalKey());
            String habitat_key = Form.requestString(hva.app.habitat.Prompt.habitatKey());
            _receiver.transferAnimalToHabitat(animal_key, habitat_key);
        }
        catch (HabitatDoNotExistException e){
            throw new UnknownHabitatKeyException(e.getKey());
        }
        catch (AnimalDoNotExistException e){
            throw new UnknownAnimalKeyException(e.getKey());
        }
        catch (AnimalExistsException e){
            throw new DuplicateAnimalKeyException(e.getKey());
        }

    }

}
