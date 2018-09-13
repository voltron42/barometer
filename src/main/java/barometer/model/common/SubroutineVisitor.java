package barometer.model.common;

public interface SubroutineVisitor {
    void visit(Procedure procedure);

    void visit(Function function);
}
