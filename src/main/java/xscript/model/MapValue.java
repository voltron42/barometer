package xscript.model;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

public class MapValue implements Value {
    private List<MapEntry> entries;

    @XmlElement(name="entry")
    public List<MapEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<MapEntry> entries) {
        this.entries = entries;
    }

    @Override
    public Object accept(ValueVisitor visitor) {
        return visitor.visit(this);
    }
}
