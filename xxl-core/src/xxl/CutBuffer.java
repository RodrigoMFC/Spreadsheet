package xxl;

import java.util.ArrayList;
import java.util.List;

import java.io.Serializable;

public class CutBuffer implements Serializable{

    private List<List<Content>> contents = new ArrayList<>();
    private int size = 0;
    private boolean horizontal = false;



    public void paste(int l, int c, Spreadsheet spreadsheet) {
    }
    public void copyHorizontal(List<Content> contentList){
        horizontal=true;
        size=contentList.size();
        contents = new ArrayList<>();
        List<Content>line= new ArrayList<>();
        for (Content content: contentList){
            line.add(content);
        }
        contents.add(line);
    }

    public void copyVertical(List<Content> contentList){
        horizontal=false;
        contents = new ArrayList<>();
        size=contentList.size();
        for(Content content: contentList){
            List<Content>line= new ArrayList<>();
            line.add(content);
            contents.add(line);
        }
    }

    public String showBuffer(){
        String outputStrings = "";
        int lin=0;
        int col=0;
        for(List<Content> contentList :contents){
            lin++;
            col=0;
            for(Content content : contentList){
                col++;
                if(!content.getValue().equals(content.getInputString())){
                    outputStrings+=(String.valueOf(lin)+";"+String.valueOf(col)+"|"+content.getValue()+content.getInputString()+"\n");
                }
                else{
                    outputStrings+=(String.valueOf(lin)+";"+String.valueOf(col)+"|"+content.getValue()+"\n");
                }
            }
        }
        outputStrings = outputStrings.substring(0, outputStrings.length() - 1);
        return outputStrings;
    }
    public List<List<Content>> getContents(){
        return contents;
    }

    public String horizontalGetString(int i){
        return contents.get(0).get(i).getInputString();
    }

    public String verticalGetString(int i){
        return contents.get(i).get(0).getInputString();
    }

    public int getSize(){
        return size;
    }
    public boolean isHorizontal(){
        return horizontal;
    }
}
