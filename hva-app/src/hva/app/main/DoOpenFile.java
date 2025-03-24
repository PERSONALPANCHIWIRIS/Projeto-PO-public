package hva.app.main;

import hva.HotelManager;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import hva.exceptions.UnavailableFileException;
import hva.app.exceptions.FileOpenFailedException;
import java.io.IOException;


class DoOpenFile extends Command<HotelManager> {
    DoOpenFile(HotelManager receiver) {
        super(Label.OPEN_FILE, receiver);
    }

    @Override
    protected final void execute() throws CommandException {
        try {
            //Caso existam mudanças e é decicido abrir outro ficheiro,
            //pergunta ao utilizador se deseja guardar o ficheiro atual
            if (_receiver.changed() && Form.confirm(Prompt.saveBeforeExit())) {
                DoSaveFile command = new DoSaveFile(_receiver);
                command.execute(); //chama o comando save
            }
            //Pede o nome do ficheiro a abrir e caso não exista lança
            //o UnavailableFileexception
            String filename = Form.requestString(Prompt.openFile());
            _receiver.load(filename); // Assuming this method throws UnavailableFileException
        } catch (UnavailableFileException e) {
            throw new FileOpenFailedException(e);
            }
            catch (IOException e) {e.printStackTrace();}
            catch (ClassNotFoundException e) {e.printStackTrace();}
            //Exceptions associadas ao input
        }
    }

