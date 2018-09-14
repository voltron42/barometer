package xscript.model;

import javax.xml.bind.annotation.XmlAttribute;

public class For extends Block implements Command {
    private String varName;
    private String initExpression;
    private String whileExpression;
    private String stepExpression;

    @XmlAttribute(name="var")
    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName = varName;
    }

    @XmlAttribute(name="init")
    public String getInitExpression() {
        return initExpression;
    }

    public void setInitExpression(String initExpression) {
        this.initExpression = initExpression;
    }

    @XmlAttribute(name="while")
    public String getWhileExpression() {
        return whileExpression;
    }

    public void setWhileExpression(String whileExpression) {
        this.whileExpression = whileExpression;
    }

    @XmlAttribute(name="step")
    public String getStepExpression() {
        return stepExpression;
    }

    public void setStepExpression(String stepExpression) {
        this.stepExpression = stepExpression;
    }

    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }
}
