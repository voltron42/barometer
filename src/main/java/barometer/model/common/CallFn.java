package barometer.model.common;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

public class CallFn implements Value {
    private String functionName;
    private List<Argument> args;

    @XmlAttribute(name="name")
    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    @XmlElement(name="arg")
    public List<Argument> getArgs() {
        return args;
    }

    public void setArgs(List<Argument> args) {
        this.args = args;
    }

    @Override
    public Object accept(ValueVisitor visitor) {
        return visitor.visit(this );
    }
}
