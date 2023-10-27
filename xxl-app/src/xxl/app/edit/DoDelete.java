package xxl.app.edit;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import xxl.Spreadsheet;
// FIXME import classes
import xxl.exceptions.UnrecognizedEntryException;

/**
 * Delete command.
 */
class DoDelete extends Command<Spreadsheet> {

    DoDelete(Spreadsheet receiver) {
        super(Label.DELETE, receiver);
        // FIXME add fields
    }

    @Override
    protected final void execute() throws CommandException {
        String range =Form.requestString(Prompt.address());
        try{
            _receiver.DoDelete(range);
        }catch (UnrecognizedEntryException e){
            throw new InvalidCellRangeException(range);
        }
    }

}
