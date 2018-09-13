package barometer.model.common;

import javax.xml.bind.annotation.XmlAttribute;

public class Prompt implements Command {
    private String messageExpression;
    private String variableName;

    @XmlAttribute(name="message")
    public String getMessageExpression() {
        return messageExpression;
    }

    public void setMessageExpression(String messageExpression) {
        this.messageExpression = messageExpression;
    }

    @XmlAttribute(name="variable")
    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }
}
