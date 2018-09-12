package parse;

public class NamedSymbol<S,T> implements Symbol<S,T> {
    private S name;

    @Override
    public T accept(SymbolVisitor<S, T> visitor) {
        return visitor.visit(this);
    }
}
