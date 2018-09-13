package barometer.model.common;

import javax.xml.bind.annotation.XmlAttribute;

public class ElseIfBlock extends Block {
    private String conditionExpression;

    @XmlAttribute(name="condition")
    public String getConditionExpression() {
        return conditionExpression;
    }

    public void setConditionExpression(String conditionExpression) {
        this.conditionExpression = conditionExpression;
    }
}
