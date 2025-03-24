package hva.app.employee;


import hva.Hotel;
import hva.app.exceptions.UnknownEmployeeKeyException;
import hva.exceptions.EmployeeDoNotExistException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import pt.tecnico.uilib.forms.Form;


class DoShowSatisfactionOfEmployee extends Command<Hotel> {

    DoShowSatisfactionOfEmployee(Hotel receiver) {
        super(Label.SHOW_SATISFACTION_OF_EMPLOYEE, receiver);
    }

    @Override
    protected void execute() throws CommandException {
        try{
        String employeeKey = Form.requestString(Prompt.employeeKey());
        double employeeSatisfaction = _receiver.employeeSatisfaction(employeeKey);
        _display.popup((int) employeeSatisfaction);
        }
    
        catch (EmployeeDoNotExistException e){
            throw new UnknownEmployeeKeyException(e.getKey());
        }
    }

}
