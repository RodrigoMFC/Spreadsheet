package xxl.cell;

public class AddCell extends Cell{
    private Cell c1;
    private Cell c2;
    private int number = 0;

    public AddCell(Cell c1, Cell c2) {
        this.c1 = c1;
        this.c2 = c2;
    }
    public AddCell(Cell c1, int number) {
        this.c1 = c1;
        this.number = number;
        this.c2.setValue("0");
    }

    @Override
    public String getValue() {
        int sum = Integer.parseInt(c1.getValue())+Integer.parseInt(c2.getValue())+number;
        return Integer.toString(sum);
    }
}
