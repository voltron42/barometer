package xscript.model;

import javax.xml.bind.annotation.XmlAttribute;

public class Pop implements Command {

    private String list;
    private String index;

    @XmlAttribute(name="list")
    public String getList() {
        return list;
    }

    public void setList(String list) {
        this.list = list;
    }

    @XmlAttribute(name="index")
    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }
}
