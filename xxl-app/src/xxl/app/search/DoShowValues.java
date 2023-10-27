package xxl.app.search;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import xxl.Spreadsheet;
// FIXME import classes
import xxl.app.edit.InvalidCellRangeException;
import xxl.exceptions.UnrecognizedEntryException;

/**
 * Command for searching content values.
 */
class DoShowValues extends Command<Spreadsheet> {

    DoShowValues(Spreadsheet receiver) {
        super(Label.SEARCH_VALUES, receiver);
        // FIXME add fields
    }

    @Override
    protected final void execute() {
        _display.popup(_receiver.ShowValue(Form.requestString(Prompt.searchValue())));
    }

}
