package barometer.model.common;

import javax.xml.bind.annotation.XmlElement;

public class Function extends Subroutine {
    private Return returnValue;

    @XmlElement(name="return")
    public Return getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(Return returnValue) {
        this.returnValue = returnValue;
    }

    @Override
    public void accept(SubroutineVisitor visitor) {
        visitor.visit(this);
    }
}
