package hva.app.search;


import hva.Hotel;
import hva.exceptions.VetDoNotExistException;
import hva.app.exceptions.UnknownVeterinarianKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import pt.tecnico.uilib.forms.Form;


class DoShowMedicalActsByVeterinarian extends Command<Hotel> {

    DoShowMedicalActsByVeterinarian(Hotel receiver) {
        super(Label.MEDICAL_ACTS_BY_VET, receiver);

    }

    @Override
    protected void execute() throws CommandException {
        try{
            String vet_key = Form.requestString(hva.app.employee.Prompt.employeeKey());
            _display.popup(_receiver.showMedicalActsByVeterinarian(vet_key));
        }
        catch(VetDoNotExistException e){
            throw new UnknownVeterinarianKeyException(e.getKey());
        }
    }

}
