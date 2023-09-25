package xxl.cell;

import java.util.ArrayList;
import java.util.List;

public class AverageCell extends Cell{
    private Interval interval;

    public AverageCell(Interval interval) {
        this.interval = interval;
    }

    @Override
    public String getValue() {
        List<Double> doubleList = interval.getValues();
        Double res = 0.0;
        for (int i=0;i<doubleList.size();i++) {
            res+=doubleList.get(i);
        }
        Double average = res / doubleList.size();
        return average.toString();
    }
}