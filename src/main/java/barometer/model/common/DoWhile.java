package barometer.model.common;

import javax.xml.bind.annotation.XmlAttribute;

public class DoWhile extends Block implements Command {
    private String whileExpression;

    @XmlAttribute(name="while")
    public String getWhileExpression() {
        return whileExpression;
    }

    public void setWhileExpression(String whileExpression) {
        this.whileExpression = whileExpression;
    }

    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }
}
