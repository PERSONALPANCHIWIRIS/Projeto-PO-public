package hva.app.vaccine;

import hva.Hotel;
import hva.app.exceptions.DuplicateVaccineKeyException;
import hva.app.exceptions.UnknownSpeciesKeyException;
import hva.exceptions.SpeciesDoNotExistException;
import hva.exceptions.VaccineExistsException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import pt.tecnico.uilib.forms.Form;


class DoRegisterVaccine extends Command<Hotel> {

    DoRegisterVaccine(Hotel receiver) {
        super(Label.REGISTER_VACCINE, receiver);

    }

    @Override
    protected final void execute() throws CommandException {
        try{
            String vaccine_key = Form.requestString(Prompt.vaccineKey());
            String vaccine_name = Form.requestString(Prompt.vaccineName());
            String species_list = Form.requestString(Prompt.listOfSpeciesKeys());
            String[] species_keys = species_list.split("\\s*,\\s*");
            _receiver.registerVaccine(vaccine_key, vaccine_name, species_keys);
        }
        catch(VaccineExistsException e){
            throw new DuplicateVaccineKeyException(e.getKey());
        }
        catch (SpeciesDoNotExistException e){
            throw new UnknownSpeciesKeyException(e.getKey());
        }
    }

}
