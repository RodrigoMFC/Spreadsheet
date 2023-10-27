package xxl.app.main;
import xxl.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import xxl.exceptions.MissingFileAssociationException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import xxl.Calculator;
// FIXME import classes


/**
 * Save to file under current name (if unnamed, query for name).
 */
class DoSave extends Command<Calculator> {

    DoSave(Calculator receiver) {
        super(Label.SAVE, receiver, xxl -> xxl.getSpreadsheet() != null);
    }

    @Override
    protected final void execute() {
        // FIXME implement command and create a local Form
        try {
            _receiver.save();
        } catch (MissingFileAssociationException e) {
        try {
            _receiver.saveAs(Form.requestString(Prompt.newSaveAs()));
        } catch (MissingFileAssociationException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        } catch (FileNotFoundException e) {
        e.printStackTrace();
        } catch (IOException e) {
        e.printStackTrace();
        }
    }

}
