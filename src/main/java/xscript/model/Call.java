package xscript.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

public class Call implements Command {
    private String procedureName;
    private List<Argument> args;

    @XmlAttribute(name="name")
    public String getProcedureName() {
        return procedureName;
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }

    @XmlElement(name="arg")
    public List<Argument> getArgs() {
        return args;
    }

    public void setArgs(List<Argument> args) {
        this.args = args;
    }

    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }
}
