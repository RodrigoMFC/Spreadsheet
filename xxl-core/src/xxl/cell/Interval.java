package xxl.cell;

import xxl.*;
import java.util.ArrayList;
import java.util.List;

public class Interval {
    private List<Cell> cellList = new ArrayList<>();
    

    /**
     * creates an interval from input list parameters
     * @param inputList 
     * @param spreedsheet current spreedsheet being used
     */
    public Interval(String[] inputList, Spreadsheet spreedsheet) {
        for(int i=Integer.valueOf(inputList[0]); i<Integer.valueOf(inputList[2]); i++) {
            for(int j=Integer.valueOf(inputList[1]); j<Integer.valueOf(inputList[3]); j++) {

                cellList.add(spreedsheet.getCell(i,j));

            }
        }
    }
    /**
     * 
     * @return cells values converted to Double
     */
    public List<Double> getValues() {
        List<Double> doubleList = new ArrayList<>();
        for (int i=0;i<cellList.size();i++) {
            doubleList.add(Double.parseDouble(cellList.get(i).getValue()));
        }
        return doubleList;
    }
}
