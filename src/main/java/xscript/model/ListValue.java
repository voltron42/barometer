package xscript.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import java.util.List;

public class ListValue implements Value {

    private List<Value> items;

    @XmlElements({
            @XmlElement(name="primitive",type=PrimitiveValue.class),
            @XmlElement(name="list",type=ListValue.class),
            @XmlElement(name="map",type=MapValue.class),
            @XmlElement(name="null",type=NullValue.class),
            @XmlElement(name="call-fn",type=CallFn.class),
    })
    public List<Value> getItems() {
        return items;
    }

    public void setItems(List<Value> items) {
        this.items = items;
    }

    @Override
    public Object accept(ValueVisitor visitor) {
        return visitor.visit(this);
    }
}
