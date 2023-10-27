package xxl.functions;
import java.io.Serializable;

import xxl.Cell;

public class Reference extends Function implements Serializable{
    private Cell ref;

    public Reference(Object ref) {
        this.ref = (Cell) ref;
    }

    public String getValue() {
        if (ref.getValue()!="") {
            return ref.getValue();
        }
        return "#VALUE";
    }

    public void delete() {
        ref = null;
    }
}
