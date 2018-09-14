package xscript.model;

import javax.xml.bind.annotation.XmlElement;

public class Try implements Command {
    private Do tryBlock;
    private Catch catchBlock;
    private Finally finallyBlock;

    @XmlElement(name="do")
    public Do getTryBlock() {
        return tryBlock;
    }

    public void setTryBlock(Do tryBlock) {
        this.tryBlock = tryBlock;
    }

    @XmlElement(name="catch")
    public Catch getCatchBlock() {
        return catchBlock;
    }

    public void setCatchBlock(Catch catchBlock) {
        this.catchBlock = catchBlock;
    }

    @XmlElement(name="finally")
    public Finally getFinallyBlock() {
        return finallyBlock;
    }

    public void setFinallyBlock(Finally finallyBlock) {
        this.finallyBlock = finallyBlock;
    }

    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visit(this);
    }
}
