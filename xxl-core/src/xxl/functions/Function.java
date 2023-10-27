package xxl.functions;

import java.io.Serializable;

import xxl.Cell;

public abstract class Function implements Serializable{
    public abstract String getValue();
    public abstract void delete();
}
