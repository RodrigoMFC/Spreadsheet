package xxl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
public class User implements Serializable {
    private String _name;
    private List<Spreadsheet> spreadsheets = new ArrayList<>();
    public User(String name){
        _name=name;
    }

    public String getName(){
        return _name;
    }
    public void addSpreadsheet(Spreadsheet spreadsheet){
        spreadsheets.add(spreadsheet);
    }
}
