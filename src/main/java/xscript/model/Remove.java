package xscript.model;

import javax.xml.bind.annotation.XmlAttribute;

public class Remove implements Command {

    private String map;
    private String key;

    @XmlAttribute(name="map")
    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    @XmlAttribute(name="key")
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }
}
