package hva.app.employee;

import hva.Hotel;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import pt.tecnico.uilib.forms.Form;

import hva.app.exceptions.UnknownEmployeeKeyException;
import hva.app.exceptions.NoResponsibilityException;
import hva.exceptions.NoResponsibilityElementException;
import hva.exceptions.EmployeeDoNotExistException;


class DoRemoveResponsibility extends Command<Hotel> {

    DoRemoveResponsibility(Hotel receiver) {
        super(Label.REMOVE_RESPONSABILITY, receiver);

    }

    @Override
    protected void execute() throws CommandException {
        try{
            String employee_key = Form.requestString(Prompt.employeeKey());
            String responsibility_key = Form.requestString(Prompt.responsibilityKey());
            _receiver.removeResponsibility(employee_key, responsibility_key);
        }
        catch(NoResponsibilityElementException e){
            throw new NoResponsibilityException(e.getKey(), e.getResponsibility());
        }
        catch(EmployeeDoNotExistException e){
            throw new UnknownEmployeeKeyException(e.getKey());
        }
    }

}
