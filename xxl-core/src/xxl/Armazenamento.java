package xxl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Armazenamento implements Serializable{

    private Cell[][] spreadsheet;    
    private int _numLin;
    private int _numCol;

    public Armazenamento(int numLin, int numCol) {
        _numCol = numCol;
        _numLin = numLin;
        this.spreadsheet = new Cell[numLin][numCol];
        for (int i = 0; i < numLin; i++) {
            for (int j = 0; j < numCol; j++) {
                this.spreadsheet[i][j] = new Cell(); // Initialize each cell with a Cell object
            }
        }
    }

    //mostra uma funcao
    public String showCell(Cell cell, String range) {
        if (cell.getContent().getFunction() == null) {
            return (range+"|"+cell.getContent().getValue());
        }
        else {
            return (range+"|"+cell.getContent().getValue()+cell.getContent().getInputString());
        }
    }

    //returns string with cells with same value
    public String SearchValue(String value) {
        String output = "";
        for (int i=0; i<_numLin; i++) {
            for (int j=0; j<_numCol; j++) {
                var currCell = spreadsheet[i][j];      
                if (currCell.getValue().equals(value)) {
                    String range = Integer.toString(i+1)+";"+Integer.toString(j+1);
                    output+=showCell(currCell, range)+"\n";
                }
            }
        }
        if (output.length()>1) {
            return output.substring(0, output.length() - 1); //removes last \n
        } 
        else { return output; } //empty }
    }

    //get function related input
    public List<String> GetFunctionsRelatedInput(String input) {
        List<String> functionsRelatedInput = new ArrayList<>();
        String[] functionNames = {"ADD", "SUB", "MUL", "DIV", "AVERAGE", "PRODUCT", "COALESCE", "CONCAT"};
        for (String name : functionNames) {
            if (name.contains(input)) {
                functionsRelatedInput.add(name);
            }
        }
        return functionsRelatedInput;
    }

    //returns string with cells of same function
    public String SearchFunction(String function) {
        String output = "";
        List<String> FuncSearch = GetFunctionsRelatedInput(function);
        for (String currFuncSearch : FuncSearch) {
            for (int i=0; i<_numLin; i++) {
                for (int j=0; j<_numCol; j++) {
                    var currCell = spreadsheet[i][j];
                    if (currCell.getInputString().startsWith("="+currFuncSearch)) {
                        String range = Integer.toString(i+1)+";"+Integer.toString(j+1);
                        output+=showCell(currCell, range)+"\n";
                    }
                }
            }
        }
        if (output.length()>1) {
            return output.substring(0, output.length() - 1); //removes last \n
        } 
        else { return output; } //empty }
    }

    //Getter
    public Cell getCell(int linIdx, int colIdx){
        return spreadsheet[linIdx-1][colIdx-1];
    }

    //Setter
    public void setCell(int linIdx, int colIdx, Cell input){
        spreadsheet[linIdx-1][colIdx-1]= input;
    }

    //prints cell content for user viewing.
    public void viewCell(int linIdx, int colIdx) {
    }

    public Cell[][] getCellList() {
        return spreadsheet;
    }

    public int getNumCol() {
        return _numCol;
    }

    public int getNumLin() {
        return _numLin;
    }
}
