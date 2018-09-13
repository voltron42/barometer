package barometer.model.common;

import javax.xml.bind.annotation.XmlAttribute;

public class PrimitiveValue implements Value {
    private PrimitiveType type;
    private String value;

    @XmlAttribute(name="type")
    public PrimitiveType getType() {
        return type;
    }

    public void setType(PrimitiveType type) {
        this.type = type;
    }

    @XmlAttribute(name="value")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public Object accept(ValueVisitor visitor) {
        return visitor.visit(this);
    }
}
