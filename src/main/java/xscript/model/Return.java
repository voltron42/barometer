package xscript.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

public class Return {
    private Value value;

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
}
