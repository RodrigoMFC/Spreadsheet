package xxl.app.edit;

import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import xxl.Spreadsheet;
// FIXME import classes
import xxl.exceptions.UnrecognizedEntryException;

/**
 * Class for inserting data.
 */
class DoInsert extends Command<Spreadsheet> {

    DoInsert(Spreadsheet receiver) {
        super(Label.INSERT, receiver);
        // FIXME add fields
    }

    @Override
    protected final void execute() throws CommandException {
        String range =Form.requestString(Prompt.address());
        String content = Form.requestString(Prompt.content());
        try{
            _receiver.DoInsert(range,content);
        }catch (UnrecognizedEntryException e){
            if(e.getEntrySpecification().matches(content)){
                throw new UnknownFunctionException(content);
            }
            else{
                throw new InvalidCellRangeException(range);
            }
        }
    }

}
