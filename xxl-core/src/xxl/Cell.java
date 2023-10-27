package xxl;

import java.io.Serializable;

public class Cell implements Serializable{
    private Content content = new Content();

    public String getValue() {
        return content.getValue();
    }

    // public String viewValue() {
    //     return content.viewValue();
    // }

    public void setValue(String value) {
        content.setValue(value);
    }

    public void setInputString(String inputString) {
        content.setInputString(inputString);
    }

    public String getInputString() {
        return content.getInputString();
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public void delete() {
        content.delete();
    }
}
