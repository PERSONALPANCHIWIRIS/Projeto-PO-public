package hva.app.animal;

import hva.Hotel;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import hva.animal.Animal;
import hva.animal.Species;
import hva.app.exceptions.DuplicateAnimalKeyException;
import hva.app.exceptions.UnknownHabitatKeyException;
import hva.exceptions.HabitatDoNotExistException;
import hva.exceptions.AnimalExistsException;

class DoRegisterAnimal extends Command<Hotel> {

    DoRegisterAnimal(Hotel receiver) {
        super(Label.REGISTER_ANIMAL, receiver);
    }

    @Override
    protected final void execute() throws CommandException {
        try{
            String animal_key = Form.requestString(Prompt.animalKey());
            String name = Form.requestString(Prompt.animalName());
            String species_key = Form.requestString(Prompt.speciesKey());
            if (_receiver.getSpecie(species_key) == null){
                String species_name = Form.requestString(Prompt.speciesName());
                _receiver.registerSpecies(new Species(_receiver, species_name, species_key));
            }
            String habitat_id = Form.requestString(hva.app.habitat.Prompt.habitatKey());
            _receiver.getHabitat(habitat_id).registerAnimal(new Animal(_receiver.getSpecie(species_key), animal_key, name,
             _receiver.getHabitat(habitat_id)));
        }
        catch (AnimalExistsException e){
            throw new DuplicateAnimalKeyException(e.getKey());
        }
        catch (HabitatDoNotExistException e) {
            throw new UnknownHabitatKeyException(e.getKey());
        }
    }

}
