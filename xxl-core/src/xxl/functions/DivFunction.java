package xxl.functions;
import java.io.Serializable;

import xxl.*;

public class DivFunction extends BinaryFunction implements Serializable{
    
    public DivFunction(Object c1, Object c2, Object ownCell) {
        super(c1,c2,ownCell);
    }

    @Override
    public String getValue() { 
        Double sum = 1.0;
        Object[] params = getList();
        Object p1 = params[0];
        Object p2 = params[1];
        try {
            if (p1 instanceof Cell c) {
                var n = Integer.parseInt(c.getValue());
                sum*=n;
            }
            else {
                int n = (int) p1;
                sum*=n;
            }
            if (p2 instanceof Cell c) {
                var n = Integer.parseInt(c.getValue());
                if (n==0) {
                    throw new java.lang.ArithmeticException();
                }
                sum/=n;
            }
            else {
                int n = (int) p2;
                if (n==0) {
                    throw new java.lang.ArithmeticException();
                }
                sum/=n;
            }
            int SumInt = (int) sum.doubleValue();
            return Integer.toString(SumInt);
        }
        catch (NumberFormatException | java.lang.NullPointerException | java.lang.ArithmeticException e) {
            return "#VALUE";
        }
    }   
}   
