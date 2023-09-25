package xxl;
import xxl.cell.*;


// FIXME import classes
import xxl.exceptions.UnrecognizedEntryException;
import java.io.Serial;
import java.io.Serializable;

/**
 * Class representing a spreadsheet.
 */
public class Spreadsheet implements Serializable {

    @Serial
    private static final long serialVersionUID = 202308312359L;

    private Cell[][] spreadsheet;

    //Contructor
    public Spreadsheet (int numLin, int numCol){
        String[][] spreadsheet = new String[numLin][/*Maybe Col*/];
    }

    //Getter
    public Cell getCell(int linIdx, int colIdx){
        return spreadsheet[linIdx-1][colIdx-1];
    }

    //Setter
    public void setCell(int linIdx, int colIdx, Cell input){
        spreadsheet[linIdx-1][colIdx-1]= input;
    }

    /**
     * Insert specified content in specified range.
     *
     * @param rangeSpecification
     * @param contentSpecification
     */
    public void insertContents(String rangeSpecification, String contentSpecification) throws UnrecognizedEntryException /* FIXME maybe add exceptions */ {
        //FIXME implement method
    }

}
