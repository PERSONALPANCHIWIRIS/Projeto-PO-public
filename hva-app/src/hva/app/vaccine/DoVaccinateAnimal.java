package hva.app.vaccine;


import hva.Hotel;
import hva.app.exceptions.UnknownAnimalKeyException;
import hva.app.exceptions.UnknownEmployeeKeyException;
import hva.app.exceptions.UnknownVaccineKeyException;
import hva.app.exceptions.VeterinarianNotAuthorizedException;
import hva.app.exceptions.UnknownVeterinarianKeyException;
import hva.exceptions.AnimalDoNotExistException;
import hva.exceptions.VetDoNotExistException;
import hva.exceptions.VaccineDoNotExistException;
import hva.exceptions.VetNotAuthorizedException;
import hva.exceptions.WrongVaccineException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import pt.tecnico.uilib.forms.Form;

class DoVaccinateAnimal extends Command<Hotel> {

    DoVaccinateAnimal(Hotel receiver) {
        super(Label.VACCINATE_ANIMAL, receiver);

    }

    @Override
    protected final void execute() throws CommandException {
        try{
            String vaccine_key = Form.requestString(Prompt.vaccineKey());
            String vet_key = Form.requestString(Prompt.veterinarianKey());
            String animal_key = Form.requestString(hva.app.animal.Prompt.animalKey());
            _receiver.vaccinateAnimal(vaccine_key, vet_key, animal_key);
        }
        catch (AnimalDoNotExistException e){
            throw new UnknownAnimalKeyException(e.getKey());
        }
        catch (VetDoNotExistException e){
            throw new UnknownVeterinarianKeyException(e.getKey());
        }
        catch (VaccineDoNotExistException e){
            throw new UnknownVaccineKeyException(e.getKey());
        }
        catch (VetNotAuthorizedException e){
            throw new VeterinarianNotAuthorizedException(e.getVetKey(), e.getSpecieKey());
        }
        catch (WrongVaccineException e){
            _display.popup(Message.wrongVaccine(e.getVaccineKey(), e.getAnimalKey()));
        }
        
    }

}
