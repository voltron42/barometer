package barometer.model.common;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

public class Param {
    private String name;
    private Value defaultValue;

    @XmlAttribute(name="name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElements({
            @XmlElement(name="primitive",type=PrimitiveValue.class),
            @XmlElement(name="list",type=ListValue.class),
            @XmlElement(name="map",type=MapValue.class),
            @XmlElement(name="null",type=NullValue.class),
            @XmlElement(name="call-fn",type=CallFn.class),
    })
    public Value getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Value defaultValue) {
        this.defaultValue = defaultValue;
    }
}
