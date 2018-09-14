package xscript.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

public class Set implements Command {
    private String variable;
    private Value value;

    @XmlAttribute(name="variable")
    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    @XmlElements({
            @XmlElement(name="primitive",type=PrimitiveValue.class),
            @XmlElement(name="list",type=ListValue.class),
            @XmlElement(name="map",type=MapValue.class),
            @XmlElement(name="null",type=NullValue.class),
            @XmlElement(name="call-fn",type=CallFn.class),
    })
    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }
}
