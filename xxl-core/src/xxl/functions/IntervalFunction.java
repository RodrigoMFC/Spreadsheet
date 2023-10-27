package xxl.functions;
import xxl.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class IntervalFunction extends Function implements Serializable{
    public abstract String getValue();

    private List<Cell> cellList = new ArrayList<Cell>();
    private Cell ownCell;

    public IntervalFunction(Object interv, Object newOwnCell) {
        ownCell = (Cell) newOwnCell;
        Interval interval = (Interval) interv;
        cellList = interval.getCellList();
    }

    public void delete() {
        ownCell = null;
        cellList = null;
    }
    public List<Cell> getCellList() {
        return cellList;
    }
}
