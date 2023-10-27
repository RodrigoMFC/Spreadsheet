package xxl;
import java.io.Serializable;
import java.util.List;

//entrada final

public class AverageCell extends Content implements Serializable{
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