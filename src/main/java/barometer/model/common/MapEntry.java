package barometer.model.common;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

public class MapEntry {
    private String key;
    private Value value;

    @XmlAttribute(name="key")
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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
}
