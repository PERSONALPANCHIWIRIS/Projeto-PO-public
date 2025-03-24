package hva.app.employee;

import hva.Hotel;

import hva.app.exceptions.NoResponsibilityException;
import hva.app.exceptions.UnknownEmployeeKeyException;
import hva.app.exceptions.UnknownHabitatKeyException;
import hva.exceptions.HabitatDoNotExistException;
import hva.exceptions.EmployeeDoNotExistException;
import hva.exceptions.NoResponsibilityElementException;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import pt.tecnico.uilib.forms.Form;


class DoAddResponsibility extends Command<Hotel> {

    DoAddResponsibility(Hotel receiver) {
        super(Label.ADD_RESPONSABILITY, receiver);

    }

    @Override
    protected void execute() throws CommandException {
        try{
            String employee_key = Form.requestString(Prompt.employeeKey());
            String responsibility_key = Form.requestString(Prompt.responsibilityKey());
            _receiver.addResponsibility(employee_key, responsibility_key);
        }
        catch(NoResponsibilityElementException e){
            throw new NoResponsibilityException(e.getKey(), e.getResponsibility());
        }
        catch(EmployeeDoNotExistException e){
            throw new UnknownEmployeeKeyException(e.getKey());
        }
        catch (HabitatDoNotExistException e){
            throw new UnknownHabitatKeyException(e.getKey());
        }

    }

}
