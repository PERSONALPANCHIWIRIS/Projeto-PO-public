package hva;

import java.io.*;

import hva.employees.Employee;
import hva.exceptions.*;
import hva.habitat.Habitat;
import hva.vaccine.Vaccine;

/**
 * Class that represents the hotel application.
 */
public class HotelManager {

    /** This is the current hotel. */
    private Hotel _hotel = new Hotel();
    private String _filename;


    /**
     * Saves the serialized application's state into the file associated to the current network.
     *
     * @throws FileNotFoundException if for some reason the file cannot be created or opened.
     * @throws MissingFileAssociationException if the current network does not have a file.
     * @throws IOException if there is some error while serializing the state of the network to disk.
     */
    //Função associada ao comando DoSaveFile
    public void save() throws FileNotFoundException, MissingFileAssociationException, IOException {
        //Caso o fichiero não tenha nome associado, irá lançar a exceção 
        //que é recebida no comando e que chama a função saveAs
        if (_filename == null || _filename.isEmpty()) {
            throw new MissingFileAssociationException();
        }
        if (_hotel.changed()) {//A função só executa ações caso existam mudanças no hotel
            //Abre a porta para a memoria com o ObjectOutuputStream
            try(ObjectOutputStream save = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(_filename)))){
                //Escreve a hotel na memoria e muda o estado "changed" de forma a reconhecer futuras
                //mudanças além das já guardadas
                save.writeObject(_hotel);
                _hotel.setChanged(false);
            }
        }
        return;
    }

    /**
     * Saves the serialized application's state into the file associated to the current network.
     *
     * @throws FileNotFoundException if for some reason the file cannot be created or opened.
     * @throws MissingFileAssociationException if the current network does not have a file.
     * @throws IOException if there is some error while serializing the state of the network to disk.
     */
    //função chamada pelo comado DoSaveFile com um nome a associar ao hotel
    public void saveAs(String filename) throws FileNotFoundException, MissingFileAssociationException, IOException {
        //Muda o nome e guarda o ficheiro
        _filename = filename;
        save();
    }

    /**
     * @param filename name of the file containing the serialized application's state
     *        to load.
     * @throws UnavailableFileException if the specified file does not exist or there is
     *         an error while processing this file.
     */
    public void load(String filename) throws UnavailableFileException, ClassNotFoundException, IOException {
        //Abre a porta para ler a memoria com o ObjectInputStream
        try(ObjectInputStream load = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filename)))){
            //O hotel é carrega da memoria e os seus atributos associados ao ambiente atual
            _hotel = (Hotel)load.readObject();
            _filename = filename;
            _hotel.setChanged(false);
        }
    }

    /**
     * Read text input file.
     *
     * @param filename name of the text input file
     * @throws ImportFileException
     */
    public void importFile(String filename) throws ImportFileException {
        _hotel.importFile(filename);
    }

    //Fução que retorna o hotel atual
    public Hotel getHotel() {
        return _hotel;
    }

    //Função que cria um novo hotel
    public void newHotel(){
        _hotel = new Hotel();
        _filename = "";
    }

    //Função que retorna o valor de verdade associado ao hotel ter mudado ou não
    public boolean changed(){
        return _hotel.changed();
    }

    public void advanceSeason(){
        _hotel.advanceSeason();
    }

    public String seasonAdvanced(){
        return _hotel.seasonAdvanced();
    }

    public double globalSatisfaction(){
        return _hotel.globalSatisfaction();
    }
}
