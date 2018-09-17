package xscript.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

public class Push implements Command {

    private String list;
    private String index;
    private Value value;

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

    @XmlElements({
            @XmlElement(name="primitive",type=PrimitiveValue.class),
            @XmlElement(name="list",type=ListValue.class),
            @XmlElement(name="map",type=MapValue.class),
            @XmlElement(name="null",type=NullValue.class),
            @XmlElement(name="date",type=DateValue.class),
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
