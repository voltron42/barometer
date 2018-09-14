package xscript.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

public class If implements Command {
    private String conditionExpression;
    private ThenBlock thenBlock;
    private List<ElseIfBlock> elseIfBlocks;
    private ElseBlock elseBlock;

    @XmlAttribute(name="condition")
    public String getConditionExpression() {
        return conditionExpression;
    }

    public void setConditionExpression(String conditionExpression) {
        this.conditionExpression = conditionExpression;
    }

    @XmlElement(name="then")
    public ThenBlock getThenBlock() {
        return thenBlock;
    }

    public void setThenBlock(ThenBlock thenBlock) {
        this.thenBlock = thenBlock;
    }

    @XmlElement(name="else-if")
    public List<ElseIfBlock> getElseIfBlocks() {
        return elseIfBlocks;
    }

    public void setElseIfBlocks(List<ElseIfBlock> elseIfBlocks) {
        this.elseIfBlocks = elseIfBlocks;
    }

    @XmlElement(name="else")
    public ElseBlock getElseBlock() {
        return elseBlock;
    }

    public void setElseBlock(ElseBlock elseBlock) {
        this.elseBlock = elseBlock;
    }

    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }
}
