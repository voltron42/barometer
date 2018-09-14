package xscript.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

public abstract class Subroutine {
    private String name;
    private String template;
    private List<Param> params;
    private VarParam varParam;
    private Do doBlock;

    @XmlAttribute(name="name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlAttribute(name="template")
    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    @XmlElement(name="param")
    public List<Param> getParams() {
        return params;
    }

    public void setParams(List<Param> params) {
        this.params = params;
    }

    @XmlElement(name="var-param")
    public VarParam getVarParam() {
        return varParam;
    }

    public void setVarParam(VarParam varParam) {
        this.varParam = varParam;
    }

    @XmlElement(name="do")
    public Do getDoBlock() {
        return doBlock;
    }

    public void setDoBlock(Do doBlock) {
        this.doBlock = doBlock;
    }

    public abstract void accept(SubroutineVisitor visitor);
}
