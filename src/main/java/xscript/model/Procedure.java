package xscript.model;

public class Procedure extends Subroutine {
    @Override
    public void accept(SubroutineVisitor visitor) {
        visitor.visit(this);
    }
}
