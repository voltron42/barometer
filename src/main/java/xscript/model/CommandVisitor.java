package xscript.model;

public interface CommandVisitor {
    void visit(Print print);

    void visit(Prompt prompt);

    void visit(Try aTry);

    void visit(While aWhile);

    void visit(Set set);

    void visit(If anIf);

    void visit(Call call);

    void visit(Do aDo);

    void visit(DoWhile doWhile);

    void visit(ForEach forEach);

    void visit(For aFor);

    void visit(Throw aThrow);

    void visit(Break aBreak);

    void visit(Continue aContinue);

    void visit(Remove remove);

    void visit(Put put);

    void visit(Pop pop);

    void visit(Push push);
}
