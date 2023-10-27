package xxl;
import xxl.exceptions.UnrecognizedEntryException;
import xxl.functions.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a spreadsheet.
 */
public class Spreadsheet implements Serializable {

    @Serial
    private static final long serialVersionUID = 202308312359L;
    private CutBuffer cutBuffer = new CutBuffer();
    private boolean _hasChanged = false;
    private Armazenamento armazenamento;
    private List<User> users = new ArrayList<>();

    //Contructor
    public Spreadsheet (int numLin, int numCol,User user){
        armazenamento = new Armazenamento(numLin, numCol);
        users.add(user);
    }

    public void addUser(User user){
        users.add(user);
    }
    //Getter
    public Cell getCell(int linIdx, int colIdx){ 
        return armazenamento.getCell(linIdx, colIdx);
    }

    //returns single cell content for user viewing.   
    public String showContents(String range) throws UnrecognizedEntryException{
        var param = parseParameter(range);
        String retStr = "";
        if (param instanceof Cell) {
            retStr+=armazenamento.showCell((Cell) param, range);
            return retStr;
        }
        else if (param instanceof Interval) {
            Interval interval = (Interval) param;
            var cellList = interval.getCellList();
            var rangeList = interval.getRangeList();
            for (int i = 0; i < cellList.size(); i++) {
                retStr+=armazenamento.showCell(cellList.get(i),rangeList.get(i))+"\n";
            }
            retStr = retStr.substring(0, retStr.length() - 1); //remove extra \n
            return retStr;
        }
        else {
            //error, that cell/s doesnt exist
            throw new UnrecognizedEntryException(range);
        }
    }
    //funcoes para implementar operacoes (funcoes chamadas no main).
    public void DoCopy(String range) throws UnrecognizedEntryException{
        var param = parseParameter(range);
        if (param instanceof Cell){
            Cell cell = (Cell) param;
            List<Content> content = new ArrayList<>();
            Content copy = new Content();
            copy.setValue(cell.getValue());
            copy.setInputString(cell.getInputString());
            content.add(copy);
            cutBuffer.copyHorizontal(content);
        }
        else{
            Interval interval = (Interval) param;
            List<Content> toCopyContent = interval.getContent();
            List<Content> content = new ArrayList<>();
            for(Content toCopy : toCopyContent){
                Content copy = new Content();
                copy.setValue(toCopy.getValue());
                copy.setInputString(toCopy.getInputString());
                content.add(copy);
            }
            
            String[] parts = range.split("\\:"); // split around ":"
            String[] arg1 = parts[0].split("\\;");
            String[] arg2 = parts[1].split("\\;");
            if (!arg1[0].matches(arg2[0])){
                cutBuffer.copyVertical(content);
            }
            else{
                cutBuffer.copyHorizontal(content);
            }
        }
        
    }


    public void DoCut(String range) throws UnrecognizedEntryException{
        DoCopy(range);
        DoDelete(range);
    }
    public void DoDelete(String range) throws UnrecognizedEntryException {
        var param = parseParameter(range);
        if (param instanceof Cell){
            Cell cell = (Cell) param;
            cell.delete();
            return;
        }
        Interval interval = (Interval) param;
        for ( Cell cell: interval.getCellList()){
            cell.delete();
        }
    }
    public void DoInsert(String range, String content) throws UnrecognizedEntryException{
        var param= parseParameter(range); // check range
        if(param instanceof Cell){
            insertContents(range, content);
            return;
        }
        Interval interval = (Interval) param;
        var rangeList = interval.getRangeList();
        for (String coord: rangeList){
            insertContents(coord, content);
        }
    }
    public void DoPaste(String range) throws UnrecognizedEntryException {
        if(cutBuffer.getContents().size()== 0){
            return;
        }
        var param = parseParameter(range);
        int numPasted=0;
        if (param instanceof Cell){  // check se célula
            String[] parts = range.split("\\:"); // split around ":"
            String[] arg1= parts[0].split("\\;");
            int minLin = Integer.parseInt(arg1[0]);
            int minCol = Integer.parseInt(arg1[1]);
            if(cutBuffer.isHorizontal()){
                for (int i=minCol; i <= armazenamento.getNumCol();i++){
                    if((cutBuffer.getSize() <= numPasted)){
                        break;
                    }
                    String coord = String.valueOf(minLin)+";"+String.valueOf(i);
                    String newInput= cutBuffer.horizontalGetString(numPasted);
                    Content newContent = parseContent(coord, newInput);
                    armazenamento.getCell(minLin,i).setContent(newContent);
                    numPasted++;
                }
            }
            else{
                for(int i=minLin; i<= armazenamento.getNumLin();i++){
                    if(cutBuffer.getSize() <= numPasted){
                        break;
                    }
                    String coord = String.valueOf(i)+";"+String.valueOf(minCol);
                    String newInput= cutBuffer.verticalGetString(numPasted);
                    Content newContent = parseContent(coord, newInput);
                    armazenamento.getCell(i,minCol).setContent(newContent);
                    numPasted++;
                }
            }
        }
        else{
            String[] parts = range.split("\\:"); // split around ":"
            String[] arg1 = parts[0].split("\\;");
            String[] arg2 = parts[1].split("\\;");
            int minLin= Integer.parseInt(arg1[0]);
            int minCol= Integer.parseInt(arg1[1]);
            int maxLin= Integer.parseInt(arg2[0]);
            int maxCol= Integer.parseInt(arg2[1]);
            if((minLin==maxLin && cutBuffer.isHorizontal()) && maxCol-minCol+1 == cutBuffer.getSize() ){
                for (int i= minCol; i<=maxCol;i++){
                    String coord = String.valueOf(minLin)+";"+String.valueOf(i);
                    String newInput= cutBuffer.horizontalGetString(numPasted);
                    Content newContent = parseContent(coord, newInput);
                    armazenamento.getCell(minLin,i).setContent(newContent);
                    numPasted++;
                }
            }
            else{
                if (maxLin-minLin+1 == cutBuffer.getSize()){
                    for (int i= minLin; i<=maxLin;i++){
                    String coord = String.valueOf(i)+";"+String.valueOf(minCol);
                    String newInput= cutBuffer.verticalGetString(numPasted);
                    Content newContent = parseContent(coord, newInput);
                    armazenamento.getCell(i,minCol).setContent(newContent);
                    numPasted++;
                }
                }
            }
        }
    }


    public String DoShowCutBuffer() {
        return cutBuffer.showBuffer();
    }

    public CutBuffer getCutBuffer(){
        return cutBuffer;
    }

    /**
     * Insert specified content in specified range.
     *
     * @param range range specification
     * @param content content specification
     */
    public void insertContents(String range, String content) throws UnrecognizedEntryException /* FIXME maybe add exceptions */ {
        Cell cell = (Cell) parseParameter(range);
        cell.setContent(parseContent(range,content));
        //parsing;
        setChanged(true);

    }   

    public Object parseParameter(String param) throws UnrecognizedEntryException { 
        if (param.matches("\\d+;\\d+")) { //is reference
            try {
                String[] parts = param.split("\\;"); // split around ";"
                int int1 = Integer.parseInt(parts[0]);
                int int2 = Integer.parseInt(parts[1]);
                return getCell(int1, int2); 
            } catch (NumberFormatException | IndexOutOfBoundsException e) { //find correct error
                // The input is not a valid number
                throw new UnrecognizedEntryException(param);
            }
        }
        else if (param.matches("^\\d+;\\d+:\\d+;\\d+$")) { //is a Interval
            try {
                String[] parts = param.split("\\:"); // split around ":"
                String[] arg1 = parts[0].split("\\;");
                String[] arg2 = parts[1].split("\\;");
                String[] strList = {arg1[0], arg1[1], arg2[0], arg2[1]};
                return new Interval(strList, this);
            } catch (NumberFormatException | IndexOutOfBoundsException e) { //find correct error
                // The input is not a valid interval
                throw new UnrecognizedEntryException(param);
            }
        }
        else {
            try {
                int int1 = Integer.parseInt(param);
                return int1;
            } catch (NumberFormatException e) {
                // The input is not a valid number
                throw new UnrecognizedEntryException(param);
            }
        }
    }

    public Content parseContent(String range, String content) throws UnrecognizedEntryException /* FIXME maybe add exceptions */ {
        String inputString = content;
        if (content.startsWith("=")) { // if function (including reference)
            
            content = content.substring(1); //remove "="
            if (content.matches(".*\\([^,]+,[^,]+\\)$") ) { // if is binary function 
                String[] parts = content.split("\\("); // split around "("
                parts[1] = parts[1].substring(0, parts[1].length() - 1); //remove last ")"  
                String[] parameters = parts[1].split("\\,");
                var par1 = parseParameter(parameters[0]);
                var par2 = parseParameter(parameters[1]);
                var ownCell = parseParameter(range);
                Function newFunction;
                switch (parts[0]) {
                    case "ADD":
                        newFunction = new AddFunction(par1,par2, ownCell);
                        break;
                    case "SUB":
                        newFunction = new SubFunction(par1,par2, ownCell);
                        break;
                    case "MUL":
                        newFunction = new MulFunction(par1,par2, ownCell);
                        break;
                    case "DIV":
                        newFunction = new DivFunction(par1,par2, ownCell);
                        break;
                    default:
                        throw new UnrecognizedEntryException(content);
                }
                Content newContent = new Content();
                newContent.setFunction(newFunction);
                newContent.setValue(newContent.getValue());
                newContent.setInputString(inputString);
                return newContent;

            }
            else if (content.matches(".*\\([^:]+:[^:]+\\)$") ) { // if is interval function 
                String[] parts = content.split("\\("); // split around "("
                parts[1] = parts[1].substring(0, parts[1].length() - 1); //remove last ")"  
                Interval interval = (Interval) parseParameter(parts[1]);
                var ownCell = parseParameter(range);
                Function newFunction;
                switch (parts[0]) {
                    case "AVERAGE":
                        newFunction = new AverageFunction(interval, ownCell);
                        break;
                    case "PRODUCT":
                        newFunction = new ProductFunction(interval, ownCell);
                        break;
                    case "CONCAT":
                        newFunction = new ConcatFunction(interval, ownCell);
                        break;
                    case "COALESCE":
                        newFunction = new CoalesceFunction(interval, ownCell);
                        break;
                    default:
                        throw new UnrecognizedEntryException(content);
                }
                Content newContent = new Content();
                newContent.setFunction(newFunction);
                newContent.setValue(newContent.getValue());
                newContent.setInputString(inputString);
                return newContent;
            }
            else if (content.matches("^\\d+;\\d+$")) { //is reference
                Content newContent = new Content();
                Reference refFunc = new Reference(parseParameter(content)); //creates ref to cell in param
                newContent.setFunction(refFunc);
                newContent.setValue(newContent.getValue());
                newContent.setInputString(inputString);
                return newContent;

            }
            else { //not accepted case
                throw new UnrecognizedEntryException(content);
            }
        }
        else if (content.matches("-?\\d+(\\.\\d+)?")) { // if number
            Content newContent = new Content();
            newContent.setValue(content);
            newContent.setInputString(inputString);
            return newContent;
            
        }
        else if(content.isEmpty()){
                Content newContent= new Content();
                return newContent;
            }
        else if (content.matches("^'.*")) { // if string
            Content newContent = new Content();
            newContent.setValue(content);
            newContent.setInputString(inputString);
            return newContent;

        }
        else { //not accepted case
            throw new UnrecognizedEntryException(content);
        }
    }


    public void setChanged(boolean change){
        this._hasChanged= change;
    }

    public boolean getChanged(){
        return _hasChanged;
    }

    public Armazenamento getArmazenamento(){
        return armazenamento;
    }

    public String ShowValue(String param) {
        return armazenamento.SearchValue(param);
    }

    public String ShowFunction(String param) {
        return armazenamento.SearchFunction(param);
    }
}
