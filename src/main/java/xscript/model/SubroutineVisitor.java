package xscript.model;

public interface SubroutineVisitor {
    void visit(Procedure procedure);

    void visit(Function function);
}
