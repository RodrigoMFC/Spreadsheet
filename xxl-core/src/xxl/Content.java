package xxl;
import xxl.functions.*;

import java.io.Serializable;

public class Content implements Serializable{

    private String value = "";
    private String inputString = "";
    private Function function;
    //private List<Cell> toUpdateList = new ArrayList<>(); solucao para entrega final

    // public void addUpdateList(Cell cell) {
    //     toUpdateList.add(cell);
    // }

    public String getValue() {
        if (function != null) {
            this.value = function.getValue();
            return this.value;
        }
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    // public String viewValue() { entrega final
    //     return value;
    // }

    public String getInputString() {
        return inputString;
    }

    public void setInputString(String inputString) {
        this.inputString = inputString;
    }

    public Function getFunction() {
        return function;
    }

    public void setFunction(Function function) {
        this.function = function;
    }

    public void setAll(String value, String inputString, Function function){
        this.value= value;
        this.inputString= inputString;
        
        this.function= function;
    }

    public void delete() {
        value = "";
        inputString = "";
        if(function!=null){
          function.delete();  
        }
        function = null;
    }
}

