package xxl.functions;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import xxl.*;

public class AverageFunction extends IntervalFunction implements Serializable{
    
    public AverageFunction(Object interv, Object ownCell) {
        super(interv,ownCell);
    }

    public void delete() {
        //final
    }

    @Override
    public String getValue() { 
        Integer sum = 0;
        List<Cell> cellList = getCellList();
        try {
            for (Cell cell : cellList) {
                int number = Integer.parseInt(cell.getValue());
                sum += number;
            }
            sum/=cellList.size();
            return Integer.toString(sum);
        }
        catch (NumberFormatException | java.lang.NullPointerException | java.lang.ArithmeticException e) {
            return "#VALUE";
        }
    }   
}   
