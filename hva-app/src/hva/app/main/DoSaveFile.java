package hva.app.main;

import hva.HotelManager;
import hva.exceptions.MissingFileAssociationException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import java.io.IOException;

class DoSaveFile extends Command<HotelManager> {
    DoSaveFile(HotelManager receiver) {
        super(Label.SAVE_FILE, receiver, r -> r.getHotel() != null);
    }

    @Override
    protected final void execute() {
    	try {//Trata da exceção IOException e caso o ficheiro já não
            //tenha um nome associado
    		_receiver.save(); 
            //num caso normal simplesmente guarda o hotel na memoria 
    	} catch (MissingFileAssociationException e) {
    		String filename = Form.requestString(Prompt.newSaveAs());
            //Caso naõ tenha um nome associado, pede um nome ao utilizador e 
            //chama o comando saveAs
            try{
                _receiver.saveAs(filename);
            }catch (IOException ne) {ne.printStackTrace();}
            catch (MissingFileAssociationException ne) {ne.printStackTrace();}
            
    	} catch (IOException e) {e.printStackTrace();}
    		
    }
}