package xxl;


import xxl.exceptions.UnrecognizedEntryException;

import java.util.ArrayList;
import java.util.List;

public class Interval {
    private List<Cell> cellList = new ArrayList<>();
    private List<String> rangeList = new ArrayList<>();
    private int minLin;
    private int minCol;
    private int maxLin;
    private int maxCol;
    /**
     * creates an interval from input list parameters
     * @param inputList 
     * @param spreadsheet current spreedsheet being used
     */
    public Interval(String[] inputList, Spreadsheet spreadsheet) throws UnrecognizedEntryException{
        minLin=Integer.valueOf(inputList[0]);
        minCol=Integer.valueOf(inputList[1]);
        maxLin=Integer.valueOf(inputList[2]);
        maxCol=Integer.valueOf(inputList[3]);
        if ((minLin!= maxLin) && (minCol != maxCol) || maxLin > spreadsheet.getArmazenamento().getNumLin() || maxCol > spreadsheet.getArmazenamento().getNumCol()){
            throw new UnrecognizedEntryException(minLin+";"+minCol+":"+maxLin+";"+maxCol);
        }
        for(int i=minLin; i<=maxLin; i++) {
            for(int j=minCol; j<=maxCol; j++) {
                cellList.add(spreadsheet.getCell(i,j));
                rangeList.add(i+";"+j);
            }
        }
    }

    /**
     * 
     * @return cells values converted to Double
     */
    public List<Double> getValues() {
        List<Double> doubleList = new ArrayList<>();
        for (int i=0;i<=cellList.size();i++) {
            doubleList.add(Double.parseDouble(cellList.get(i).getValue()));
        }
        return doubleList;
    }
    public List<Content> getContent(){
        List<Content> contentList = new ArrayList<>();
        for (int i=0;i<cellList.size();i++) {
            Content copy = new Content();
            Content current = cellList.get(i).getContent();
            copy.setAll(current.getValue(), current.getInputString(), current.getFunction());
            contentList.add(copy);
        }
        return contentList;
    } 
    
    public List<Cell> getCellList() {
        return cellList;
    }

    public List<String> getRangeList() {
        return rangeList;
    } 
    public int getMinLin(){
        return minLin;
    }
    public int getMinCol(){
        return minCol;
    }
    public int getMaxCol(){
        return maxCol;
    }
    public int getMaxLin(){
        return maxLin;
    }
}