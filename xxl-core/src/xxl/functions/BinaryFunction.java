package xxl.functions;
import java.io.Serializable;

import xxl.Cell;
import xxl.Content;

public abstract class BinaryFunction extends Function implements Serializable{
    private Object[] parameterList = new Object[2];
    private Cell ownCell;

    public BinaryFunction(Object obj1, Object obj2, Object newOwnCell) {
        this.ownCell = (Cell) newOwnCell;
        parameterList[0] = obj1;
        parameterList[1] = obj2;
    }

    public abstract String getValue();

    public void delete() {
        parameterList = null;
        ownCell = null;
    }       
    
    public Object[] getList() {
        return parameterList;
    }
}
