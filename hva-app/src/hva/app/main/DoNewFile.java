package hva.app.main;

import hva.HotelManager;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoNewFile extends Command<HotelManager> {
    DoNewFile(HotelManager receiver) {
        super(Label.NEW_FILE, receiver);
    }

    //Comando que cria um novo ficheiro
    @Override
    protected final void execute() throws CommandException {
        //Caso existam mudanças no hotel atual, é perguntado ao utilizador se deseja guardar 
        if (_receiver.changed() && Form.confirm(Prompt.saveBeforeExit())) {
            DoSaveFile command = new DoSaveFile(_receiver);
            command.execute();
            //É chamado o comando save
        }
        _receiver.newHotel();
    }
}
