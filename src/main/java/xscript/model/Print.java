package xscript.model;

import javax.xml.bind.annotation.XmlAttribute;

public class Print implements Command {
    private String valueExpression;

    @XmlAttribute(name="value")
    public String getValueExpression() {
        return valueExpression;
    }

    public void setValueExpression(String valueExpression) {
        this.valueExpression = valueExpression;
    }

    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }
}
