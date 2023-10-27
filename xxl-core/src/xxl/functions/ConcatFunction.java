package xxl.functions;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import xxl.*;

public class ConcatFunction extends IntervalFunction implements Serializable{
    
    public ConcatFunction(Object interv, Object ownCell) {
        super(interv,ownCell);
    }

    public void delete() {
        //final
    }

    @Override
    public String getValue() { 
        String sum = "";
        List<Cell> cellList = getCellList();
        try {
            for (Cell cell : cellList) {
                String string = cell.getValue();
                if (string.charAt(0) == '\'') {
                    String newString = string.substring(1);
                    sum += newString;
                }
            }
            return "'"+(sum);
        }
        catch (NumberFormatException | java.lang.NullPointerException | java.lang.ArithmeticException e) {
            return "#VALUE";
        }
    }   
}   
