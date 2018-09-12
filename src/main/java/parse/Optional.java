package parse;

import java.util.List;

public class Optional<S, T> extends TermList<S, T> implements Symbol<S, T>  {
    public Optional(List<Term<S, T>> terms) {
        super(terms);
    }

    @Override
    public T accept(SymbolVisitor<S, T> visitor) {
        return visitor.visit(this);
    }
}
