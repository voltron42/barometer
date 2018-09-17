package xscript.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import java.util.List;

public abstract class Block {
    private List<Command> commands;

    @XmlElements({
            @XmlElement(name="set",type=Set.class),
            @XmlElement(name="push",type=Push.class),
            @XmlElement(name="pop",type=Pop.class),
            @XmlElement(name="put",type=Put.class),
            @XmlElement(name="remove",type=Remove.class),
            @XmlElement(name="print",type=Print.class),
            @XmlElement(name="prompt",type=Prompt.class),
            @XmlElement(name="for",type=For.class),
            @XmlElement(name="for-each",type=ForEach.class),
            @XmlElement(name="while",type=While.class),
            @XmlElement(name="do-while",type=DoWhile.class),
            @XmlElement(name="if",type=If.class),
            @XmlElement(name="call",type=Call.class),
            @XmlElement(name="break",type=Break.class),
            @XmlElement(name="continue",type=Continue.class),
            @XmlElement(name="throw",type=Throw.class),
    })
    public List<Command> getCommands() {
        return commands;
    }

    public void setCommands(List<Command> commands) {
        this.commands = commands;
    }
}
