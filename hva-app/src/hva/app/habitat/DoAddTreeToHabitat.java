package hva.app.habitat;


import hva.Hotel;
import hva.app.exceptions.DuplicateTreeKeyException;
import hva.app.exceptions.UnknownHabitatKeyException;
import hva.exceptions.HabitatDoNotExistException;
import hva.exceptions.TreeExistsException;
import hva.exceptions.HabitatDoNotExistException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import pt.tecnico.uilib.forms.Form;
import hva.habitat.Habitat;
import hva.habitat.Tree;

class DoAddTreeToHabitat extends Command<Hotel> {

    DoAddTreeToHabitat(Hotel receiver) {
        super(Label.ADD_TREE_TO_HABITAT, receiver);
    }

    @Override
    protected void execute() throws CommandException {
        try{
            String habitat_key = Form.requestString(Prompt.habitatKey());
            String tree_key = Form.requestString(Prompt.treeKey());
            String treeName = Form.requestString(Prompt.treeName());
            int treeAge = Form.requestInteger(Prompt.treeAge());
            double treeDifficulty = Form.requestReal(Prompt.treeDifficulty());
            String treeType;
            while (true) {
            treeType = Form.requestString(Prompt.treeType());
            if (treeType.equals("PERENE") || treeType.equals("CADUCA")) {
                break;
            }
            }
            _receiver.getHabitat(habitat_key).registerTree(tree_key, treeName,
             treeAge, treeDifficulty, treeType);
             _display.popup(_receiver.getHabitat(habitat_key).getTree(tree_key).toString());
        }
        catch (HabitatDoNotExistException e) {
            throw new UnknownHabitatKeyException(e.getKey());
        }
        catch (TreeExistsException e) {
            throw new DuplicateTreeKeyException(e.getKey());
        }
    }

}
