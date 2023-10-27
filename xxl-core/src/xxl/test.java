package xxl;
import xxl.exceptions.UnrecognizedEntryException;
import xxl.functions.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class test {

    public static void main(String[] args) throws UnrecognizedEntryException{
        // ADD MORE
        
        List<String> exemplos = new ArrayList<>();
        exemplos.add("1;1|10");
        exemplos.add("1;2|5");
        exemplos.add("1;3|=1;2");
        exemplos.add("2;1|'a minha");
        exemplos.add("2;2|7");
        exemplos.add("4;1|=ADD(1;1,1;2)");
        exemplos.add("2;3|' picha e enorme");
        exemplos.add("3;2|=MUL(5;2,1;2)");
        exemplos.add("3;2|=DIV(1,0)");
        exemplos.add("3;2|=AVERAGE(1;1:1;3)");
        exemplos.add("3;3|=PRODUCT(1;1:1;3)");
        exemplos.add("3;4|=CONCAT(2;1:2;3)");
        exemplos.add("3;5|=COALESCE(1;1:1;3)");

        Spreadsheet spreadsheet = new Spreadsheet(40,40, new User("banana"));
        for (String content : exemplos) {
            String[] parts = content.split("\\|"); // split around "("
            spreadsheet.insertContents(parts[0],parts[1]);
        }
        spreadsheet.DoCopy("1;1:2;1");
        spreadsheet.DoPaste("3;1:3;1");
        System.out.println(spreadsheet.showContents("3;1:4;1"));
    }
}
