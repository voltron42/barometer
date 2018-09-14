package xscript.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import java.util.List;

public class VarParam {
    private String name;
    private List<Value> defaultValue;

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
            @XmlElement(name="date",type=DateValue.class),
            @XmlElement(name="call-fn",type=CallFn.class),
    })
    public List<Value> getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(List<Value> defaultValue) {
        this.defaultValue = defaultValue;
    }
}
