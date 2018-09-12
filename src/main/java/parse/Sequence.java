package parse;

import java.util.List;

public class Sequence<S, T> extends TermList<S, T> implements Symbol<S, T>  {
    public Sequence(List<Term<S, T>> terms) {
        super(terms);
    }

    @Override
    public T accept(SymbolVisitor<S, T> visitor) {
        return visitor.visit(this);
    }
}
