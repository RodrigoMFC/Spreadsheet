package xxl.functions;
import java.io.Serializable;

import xxl.*;

public class AddFunction extends BinaryFunction implements Serializable{

    public AddFunction(Object c1, Object c2, Object ownCell) {
        super(c1,c2,ownCell);
    }

    @Override
    public String getValue() { 
        int sum = 0;
        Object[] params = getList();
        Object p1 = params[0];
        Object p2 = params[1];
        try {
            if (p1 instanceof Cell c) {
                var n = Integer.parseInt(c.getValue());
                sum+=n;
            }
            else {
                int n = (int) p1;
                sum+=n;
            }
            if (p2 instanceof Cell c) {
                var n = Integer.parseInt(c.getValue());
                sum+=n;
            }
            else {
                int n = (int) p2;
                sum+=n;
            }
            return Integer.toString(sum);
        }
        catch (NumberFormatException | java.lang.NullPointerException e) {
            return "#VALUE";
        }
    }   
}
