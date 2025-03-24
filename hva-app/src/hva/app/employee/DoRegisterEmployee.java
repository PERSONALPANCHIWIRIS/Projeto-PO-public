package hva.app.employee;

import hva.Hotel;
import hva.employees.Employee;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import pt.tecnico.uilib.forms.Form;

import hva.app.exceptions.DuplicateEmployeeKeyException;
import hva.app.exceptions.UnknownHabitatKeyException;
import hva.exceptions.EmployeeExistsException;
import hva.exceptions.HabitatDoNotExistException;


class DoRegisterEmployee extends Command<Hotel> {

    DoRegisterEmployee(Hotel receiver) {
        super(Label.REGISTER_EMPLOYEE, receiver);
    }

    @Override
    protected void execute() throws CommandException {
        try{
            String employee_key = Form.requestString(Prompt.employeeKey());
            String employee_name = Form.requestString(Prompt.employeeName());
            String employee_type;
            while(true){
                employee_type = Form.requestString(Prompt.employeeType());
                if(employee_type.equals("VET") || employee_type.equals("TRT")){
                    break;
                }
            }
            _receiver.registerEmployee(_receiver, employee_name, employee_key, new String[0], employee_type);
        }
        catch (EmployeeExistsException e) {
            throw new DuplicateEmployeeKeyException(e.getKey());
        }
        //Sera lançada pelo construtor de Handler
        catch (HabitatDoNotExistException e) {
            throw new UnknownHabitatKeyException(e.getKey());
        }
    }

}
