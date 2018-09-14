package xscript.model;

import javax.xml.bind.annotation.XmlAttribute;

public class Catch extends Block {
    private String error;

    @XmlAttribute(name = "error")
    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
