package xxl.functions;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import xxl.*;

public class CoalesceFunction extends IntervalFunction implements Serializable{
    
    public CoalesceFunction(Object interv, Object ownCell) {
        super(interv,ownCell);
    }

    public void delete() {
        //final
    }

    @Override
    public String getValue() { 
        String sum = "'";
        List<Cell> cellList = getCellList();
        try {
            for (Cell cell : cellList) {
                String string = cell.getValue();
                if (!string.isEmpty() && string.charAt(0) == '\'') {
                    sum = string;
                    break;
                }
            }
            return (sum);
        }
        catch (NumberFormatException | java.lang.NullPointerException | java.lang.ArithmeticException e) {
            return "#VALUE";
        }
    }   
}   
