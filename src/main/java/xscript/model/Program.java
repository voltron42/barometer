package xscript.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import java.util.List;

public class Program {
    private List<Subroutine> subroutines;
    private Do mainRoutine;

    @XmlElements({
        @XmlElement(name="function",type=Function.class),
        @XmlElement(name="procedure",type=Procedure.class),
    })
    public List<Subroutine> getSubroutines() {
        return subroutines;
    }

    public void setSubroutines(List<Subroutine> subroutines) {
        this.subroutines = subroutines;
    }

    @XmlElement(name="main")
    public Do getMainRoutine() {
        return mainRoutine;
    }

    public void setMainRoutine(Do mainRoutine) {
        this.mainRoutine = mainRoutine;
    }
}
