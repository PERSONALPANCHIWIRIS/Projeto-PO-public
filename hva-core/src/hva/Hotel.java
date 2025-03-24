package hva;


import java.io.Serial;
import java.io.Serializable;
import java.nio.Buffer;
import java.io.BufferedReader;
import java.io.FileReader;

import java.io.IOException;
import hva.exceptions.ImportFileException;
import hva.exceptions.UnrecognizedEntryException;
import hva.exceptions.HabitatExistsException;
import hva.exceptions.AnimalDoNotExistException;
import hva.exceptions.AnimalExistsException;
import hva.exceptions.HabitatDoNotExistException;
import hva.exceptions.EmployeeExistsException;
import hva.exceptions.NoResponsibilityElementException;
import hva.exceptions.EmployeeDoNotExistException;
import hva.exceptions.SpeciesDoNotExistException;
import hva.exceptions.VaccineExistsException;
import hva.exceptions.VaccineDoNotExistException;
import hva.exceptions.VetDoNotExistException;
import hva.exceptions.VetNotAuthorizedException;
import hva.exceptions.WrongVaccineException;

import hva.habitat.Habitat;
import hva.animal.Species;
import hva.calculator.SatisfactionCalculator;
import hva.habitat.Tree;
import hva.vaccine.Vaccine;
import hva.habitat.Caduca;
import hva.habitat.Perene;
import hva.animal.Animal;
import hva.employees.Employee;
import hva.employees.Handler;
import hva.employees.Vet;
import hva.seasons.Season;
import hva.seasons.SeasonState;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Collection;
import java.util.Collections;


public class Hotel implements Serializable {

    @Serial
    private static final long serialVersionUID = 202407081733L;
    private boolean changed = false;
    private Map<String, Habitat> _habitats = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    private Map<String, Species> _species = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    //Este atributo será utilizado para armazenar as árvores de todo o hotel
    private Map<String, Tree> _all_trees = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    private Map<String, Employee> _employees = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    private Map<String, Vaccine> _vaccines = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    private Season _season;
    private SatisfactionCalculator _calculator = new SatisfactionCalculator();
    List<String> _recordedVaccinations = new ArrayList<>();
    List<String> _wrongVaccinations = new ArrayList<>();
    
    //Construtor vazio
    public Hotel() {
        _season = new Season();
    }

    //Cada uma destas funções insere o elemento no respetivo mapa
    //Com o seu id como chave e muda o estado "changed" do hotel
    public void registerHabitat(Habitat habitat) throws HabitatExistsException {
        if (_habitats.containsKey(habitat.getKey())) {
            throw new HabitatExistsException(habitat.getKey());
        }
        _habitats.put(habitat.getKey(), habitat);
        setChanged(true);
    }

    public void registerSpecies(Species specie) {
        _species.put(specie.getKey(), specie);
        setChanged(true);
    }

    //Esta função chama ao metodo register da classe habitat
    //dado que o hotel não tem uma coleção direta de animais
    public void registerAnimal(Animal animal) throws AnimalExistsException {
        Habitat habitat = animal.getHabitat();
        habitat.registerAnimal(animal);
        setChanged(true);
    }

    public void registerEmployee(Hotel hotel, String employee_name, String employee_key,
     String[] responsibility, String employee_type) throws EmployeeExistsException, HabitatDoNotExistException {
        if (_employees.containsKey(employee_key)){
            throw new EmployeeExistsException(employee_key);
        }
        switch(employee_type){
            case "VET":
                _employees.put(employee_key, new Vet(this, employee_name, employee_key,
                 responsibility));
                break;
            case "TRT":
                _employees.put(employee_key, new Handler(this, employee_name, employee_key,
                responsibility));
                break;
        }
        setChanged(true);
    }

    public void registerVaccine(String vaccine_key, String vaccine_name, String[] species_keys) throws
     VaccineExistsException, SpeciesDoNotExistException {
        if (_vaccines.containsKey(vaccine_key)) {
            throw new VaccineExistsException(vaccine_key);
        }
        for (String species_key : species_keys) {
            if (!_species.containsKey(species_key)) {
                throw new SpeciesDoNotExistException(species_key);
            }
        }
        _vaccines.put(vaccine_key, new Vaccine(this, vaccine_name, vaccine_key, species_keys));
        setChanged(true);
    }

    /**
     * Read text input file and create domain entities.
     *
     * @param filename name of the text input file
     * @throws ImportFileException
     */
    void importFile(String filename) throws ImportFileException {
        //Vai ler o ficheiro do tipo .import
	try (BufferedReader reader = new BufferedReader(new FileReader(filename))) { ;
        String line;
        while ((line = reader.readLine()) != null) {
            //Separa cada linha do import pelo carater "|"
            String[] elements = line.split("\\|");
            switch ((elements[0])) {//Caso associado a cada tipo de objeto
                case "ESPÉCIE":
                    //regista a especie
                    registerSpecies(new Species(this, elements[2], elements[1]));
                    break;

                case "HABITAT":
                    //lida com a exceção
                    try {
                        //regista o habitat
                        registerHabitat(new Habitat(this, elements[1], elements[2],
                         Integer.parseInt(elements[3]), elements.length > 4 ? elements[4].split(",") : new String[0]));
                    } catch (HabitatExistsException e) {
                        throw new UnrecognizedEntryException(line);
                    }
                    break;
                
                case "ÁRVORE":
                    //Regista a arvore dependendo do tipo da mesma
                    String _type = elements[5];
                    if (_type.equals("PERENE")){
                        //Regista uma perene
                        _all_trees.put(elements[1], new Perene(this, elements[1], elements[2],
                        Integer.parseInt(elements[3]), Double.parseDouble(elements[4]), Integer.parseInt(this.seasonAdvanced())));
                        setChanged(true);
                    }
                    else if (_type.equals("CADUCA")){
                        //Regista uma caduca
                        _all_trees.put(elements[1], new Caduca(this, elements[1], elements[2],
                        Integer.parseInt(elements[3]), Double.parseDouble(elements[4]), Integer.parseInt(this.seasonAdvanced())));
                        setChanged(true);
                    }
                    else {
                        //Caso nenhum dos casos corresponda a um 
                        //dos tipos de arvores associadas ao hotel
                        //lança a exception
                        throw new UnrecognizedEntryException(line);
                    }
                    break;
                case "ANIMAL":
                    //Regista o animal
                    try{
                        registerAnimal(new Animal(_species.get(elements[3]), elements[1],
                     elements[2], _habitats.get(elements[4])));
                    }
                    catch (AnimalExistsException e) {
                        throw new UnrecognizedEntryException(line);
                    }
                    break;

                case "TRATADOR":
                    //Regista o Tratador
                    try{
                        _employees.put(elements[1], new Handler(this, elements[2], elements[1],
                         elements.length > 3 ? elements[3].split(",") : new String[0]));
                         setChanged(true);
                    } catch (HabitatDoNotExistException e) {
                        throw new UnrecognizedEntryException(line);
                    }
                    break;

                case "VETERINÁRIO":
                //Regista o veterinario
                    _employees.put(elements[1], new Vet(this, elements[2], elements[1],
                     elements.length > 3 ? elements[3].split(",") : new String[0]));
                     setChanged(true);
                    break;

                case "VACINA":
                //Regista a vacina
                    _vaccines.put(elements[1], new Vaccine(this, elements[2], elements[1], 
                    elements.length > 3 ? elements[3].split(",") : new String[0]));
                    setChanged(true);
                    break;
                //Caso nenhum import corresponda a algum dado esperado continua o programa
                default:
                    continue;
            }

        } 
        } catch (IOException | UnrecognizedEntryException  e) {
            throw new ImportFileException(filename, e);
        }
    }

    //Muda o estado "changed" do hotel
    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    //Retorna o estado "changed" atual do hotel
    public boolean changed() {
        return changed;
    }

    //Retorna uma arvore especifica
    public Tree getTree(String treeKey){
        return _all_trees.get(treeKey);
    }

    //Retorna um habitat expecifico
    public Habitat getHabitat(String habitatKey) throws HabitatDoNotExistException{
        if (!_habitats.containsKey(habitatKey)) {
            throw new HabitatDoNotExistException(habitatKey);
        }
        return _habitats.get(habitatKey);
    }

    //Retorna um animal em especifico
    public Animal getAnimal(String animalKey){
        for (Habitat habitat : _habitats.values()) {
            Animal animal = habitat.getAnimal(animalKey);
            if (animal != null) {
                return animal;
            }
        }
        return null;
    }

    public Employee getEmployee(String employee_key){
        return _employees.get(employee_key);
    }

    //Retorna todos os habitats
    public Map<String, Habitat> getHabitats(){
        return _habitats;
    }

    //Retorna uma especie em especifico
    public Species getSpecies(String speciesKey){
        return _species.get(speciesKey);
    }

    public Collection<String> getHabitatsCollection() {
        List<String> All_List = new ArrayList<>();
        List<Habitat> habitatsList = new ArrayList<>(_habitats.values());
        
        habitatsList.sort((h1, h2) -> h1.getKey().compareToIgnoreCase(h2.getKey()));
        
        for (Habitat habitat : habitatsList) {
            All_List.add(habitat.toString());
            List<Tree> treesList = new ArrayList<>(habitat.getTrees().values());
            
            treesList.sort((t1, t2) -> t1.getKey().compareToIgnoreCase(t2.getKey()));
            
            for (Tree tree : treesList) {
                All_List.add(tree.toString());
            }
        }
        
        return Collections.unmodifiableCollection(All_List);
    }

    public Collection<String> getAnimalsCollection() {  
        List<String> All_List = new ArrayList<>();
        List<Animal> animalsList = new ArrayList<>();

        for (Habitat habitat : _habitats.values()) {
            animalsList.addAll(habitat.getAnimals().values());
        }

        animalsList.sort((a1, a2) -> a1.getKey().compareToIgnoreCase(a2.getKey()));
        for (Animal animal : animalsList) {
            All_List.add(animal.toString());
        }

        return Collections.unmodifiableCollection(All_List);
    }

    public Collection<String> getEmployeesCollection() {
        List<String> All_List = new ArrayList<>();
        List<Employee> employeesList = new ArrayList<>(_employees.values());
        
        employeesList.sort((e1, e2) -> e1.getKey().compareToIgnoreCase(e2.getKey()));
        
        for (Employee employee : employeesList) {
            All_List.add(employee.toString());
        }
        
        return Collections.unmodifiableCollection(All_List);
    }

    public Collection<String> getVaccinesCollection() {
        List<String> All_List = new ArrayList<>();
        List<Vaccine> vaccinesList = new ArrayList<>(_vaccines.values());
        
        vaccinesList.sort((v1, v2) -> v1.getKey().compareToIgnoreCase(v2.getKey()));
        
        for (Vaccine vaccine : vaccinesList) {
            All_List.add(vaccine.toString());
        }
        
        return Collections.unmodifiableCollection(All_List);
    }

    public Collection<String> getRecordedVaccines(){
        return Collections.unmodifiableCollection(_recordedVaccinations);
    }

    public Collection<String> getWrongVaccinations(){
        return Collections.unmodifiableCollection(_wrongVaccinations);
    }

    public List<String> getMedicalActsOnAnimal(String animalKey) throws AnimalDoNotExistException {
        Animal animal = getAnimal(animalKey);
        if (animal == null) {
            throw new AnimalDoNotExistException(animalKey);
        }
        return animal.getMedicalActs();
    }

    public Species getSpecie(String specieKey) {
        return _species.get(specieKey);
    }


    public SeasonState getCurrentSeason() {
        return _season.getCurrentSeason();
    }

    public void advanceSeason() {
        _season.advanceSeason();
        updateAllTreesBiocycle();
        setChanged(true);
    }


    private void updateAllTreesBiocycle() {
        for (Habitat habitat : _habitats.values()) {
            for (Tree tree : habitat.getTrees().values()) {
                tree.updateBiocycle();
            }
        }
        for (Tree tree : _all_trees.values()) {
            tree.updateBiocycle();
        }
    }

    public String seasonAdvanced(){
        SeasonState season = _season.getCurrentSeason();
        switch (season.toString()) {
            case "Spring":
                return "0";
            case "Summer":
                return "1";
            case "Fall":
                return "2";
            case "Winter":
                return "3";
            default:
                return "0";
        }
    }

    public double globalSatisfaction() {
        double satisfaction = 0;
        for (Habitat habitat : _habitats.values()) {
            satisfaction += habitat.satisfaction(_calculator);
        }
        return Math.round(satisfaction);
    }

    public double animalSatisfaction(String animalKey) throws AnimalDoNotExistException {
        Animal animal = getAnimal(animalKey);
        if (animal == null) {
            throw new AnimalDoNotExistException(animalKey);
        }
        return Math.round(animal.accept(_calculator));
    }

    public double employeeSatisfaction(String employeeKey) throws EmployeeDoNotExistException {
        Employee employee = getEmployee(employeeKey);
        if (employee == null) {
            throw new EmployeeDoNotExistException(employeeKey);
        }
        return Math.round(employee.accept(_calculator));
    }

    public void transferAnimalToHabitat(String animalKey, String habitatKey) throws
     HabitatDoNotExistException, AnimalDoNotExistException, AnimalExistsException {
        if (!_habitats.containsKey(habitatKey)) {
            throw new HabitatDoNotExistException(habitatKey);
        }
        if (getAnimal(animalKey) == null) {
            throw new AnimalDoNotExistException(animalKey);
        }
        Animal animal = getAnimal(animalKey);
        Habitat habitat = getHabitat(habitatKey);
        animal.getHabitat().removeAnimal(animal);
        habitat.registerAnimal(animal);
        animal.setHabitat(habitat);
        setChanged(true);
    }

    public void addResponsibility(String employeeKey, String responsibilityKey) throws NoResponsibilityElementException,
      EmployeeDoNotExistException, HabitatDoNotExistException {
        if (!_habitats.containsKey(responsibilityKey) && getSpecie(responsibilityKey) == null) {
            throw new NoResponsibilityElementException(employeeKey, responsibilityKey);
        }
        Employee employee = getEmployee(employeeKey);
        if (employee == null) {
            throw new EmployeeDoNotExistException(employeeKey);
        }
        String employee_type = employee.getWork();
        switch(employee_type){
            case "VET":
                if (getSpecie(responsibilityKey) == null) {
                    throw new NoResponsibilityElementException(employeeKey, responsibilityKey);
                }
                Species specie = getSpecie(responsibilityKey);
                Vet vet = (Vet) employee;
                vet.addResponsibility(specie);
                break;
            case "TRT":
            //Verifica se a responsabilidade, mesmo existindo, está associada ao tratatdor (é um habitat)
                if (!_habitats.containsKey(responsibilityKey)) {
                    throw new NoResponsibilityElementException(employeeKey, responsibilityKey);
                }
                Habitat habitat = getHabitat(responsibilityKey);
                Handler handler = (Handler) employee;
                handler.addResponsibility(habitat);
                break;
        }
        setChanged(true);
    }

    public void removeResponsibility(String employeeKey, String responsibilityKey) throws NoResponsibilityElementException,
      EmployeeDoNotExistException{
        if (!_habitats.containsKey(responsibilityKey) && getSpecie(responsibilityKey) == null) {
            throw new NoResponsibilityElementException(employeeKey, responsibilityKey);
        }
        Employee employee = getEmployee(employeeKey);
        if (employee == null) {
            throw new EmployeeDoNotExistException(employeeKey);
        }
        String employee_type = employee.getWork();
        switch(employee_type){
            case "VET":
                Vet vet = (Vet) employee;
                Species specie = vet.getSpecie(responsibilityKey);
                if (specie == null) {
                    throw new NoResponsibilityElementException(employeeKey, responsibilityKey);
                }
                vet.removeResponsibility(specie);
                break;
            case "TRT":
                Handler handler = (Handler) employee;
                Habitat habitat = handler.getHabitat(responsibilityKey);
                if (habitat == null) {
                    throw new NoResponsibilityElementException(employeeKey, responsibilityKey);
                }
                handler.removeResponsibility(habitat);
                break;
        }
        setChanged(true);
    }

    public int getNumberofHandlers(Habitat habitat){
        int total = 0;
        for (Employee employee : _employees.values()) {
            if (employee instanceof Handler) {
                Handler handler = (Handler) employee;
                if (handler.getHabitat(habitat.getKey()) != null) {
                    total++;
                }
            }
        }
        return total;
    }

    public int getNumberofVets(Species specie){
        int total = 0;
        for (Employee employee : _employees.values()) {
            if (employee instanceof Vet) {
                Vet vet = (Vet) employee;
                if (vet.getSpecie(specie.getKey()) != null) {
                    total++;
                }
            }
        }
        return total;
    }

    public int getAnimalPopulation(Species specie){
        int population = 0;
        for (Habitat habitat : _habitats.values()) {
            Map<String, Animal> animals = habitat.getAnimals();
            for (Animal animal : animals.values()) {
                if (animal.getSpecie().equals(specie)) {
                    population++;
                }
            }
        }
        return population;
    }

    public void vaccinateAnimal(String vaccineKey, String vetKey, String animalKey) throws
     AnimalDoNotExistException, VetDoNotExistException, VaccineDoNotExistException, VetNotAuthorizedException,
     WrongVaccineException {
        Animal animal = getAnimal(animalKey);
        if (animal == null) {
            throw new AnimalDoNotExistException(animalKey);
        }
        Employee employee = getEmployee(vetKey);
        if (employee == null || !(employee instanceof Vet)) {
            throw new VetDoNotExistException(vetKey);
        }
        Vet vet = (Vet) employee;
        if (vet.getSpecie(animal.getSpecie().getKey()) == null) {
            throw new VetNotAuthorizedException(getAnimal(animalKey).getSpecie().getKey(), vetKey);
        }
        Vaccine vaccine = _vaccines.get(vaccineKey);
        if (vaccine == null) {
            throw new VaccineDoNotExistException(vaccineKey);
        }
        boolean wronged = animal.vaccinate(vaccine);
        _recordedVaccinations.add( "REGISTO-VACINA|" + vaccineKey + "|" + vetKey + "|"
         + getAnimal(animalKey).getSpecie().getKey());
        setChanged(true);
        if (wronged) {
            _wrongVaccinations.add( "REGISTO-VACINA|" + vaccineKey + "|" + vetKey + "|"
            + getAnimal(animalKey).getSpecie().getKey());
            throw new WrongVaccineException(vaccineKey, animalKey);
        }
    }

    public Collection<String> showMedicalActsByVeterinarian(String vetKey) throws VetDoNotExistException {
        List<String> medicalActs = new ArrayList<>();
        Employee employee = getEmployee(vetKey);
        if (employee == null || employee.getWork().equals("TRT")) {
            throw new VetDoNotExistException(vetKey);
        }
        for (String vaccination : _recordedVaccinations) {
            String[] elements = vaccination.split("\\|");
            if (elements[2].equals(vetKey)) {
                medicalActs.add(vaccination);
            }
        }
        return Collections.unmodifiableCollection(medicalActs);
    }

}
