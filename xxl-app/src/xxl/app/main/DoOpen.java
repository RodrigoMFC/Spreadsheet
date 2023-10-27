package xxl.app.main;

import java.io.FileNotFoundException;
import java.io.IOException;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import xxl.Calculator;
// FIXME import classes
import xxl.app.main.FileOpenFailedException;
import xxl.exceptions.UnavailableFileException;

/**
 * Open existing file.
 */
class DoOpen extends Command<Calculator> {

    DoOpen(Calculator receiver) {
        super(Label.OPEN, receiver);
    }

    @Override
    protected final void execute() throws CommandException {
        try {
            if (_receiver.getSpreadsheet()!= null && _receiver.changed() && Form.confirm(Prompt.saveBeforeExit())) {
                DoSave cmd = new DoSave(_receiver);
                cmd.execute();
            }
            String filename = Form.requestString(Prompt.openFile());
             _receiver.load(filename);
            } catch (UnavailableFileException e) {
                throw new FileOpenFailedException(e);
            } catch (FileNotFoundException e) {
                _display.popup(Message.fileNotFound());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
}
