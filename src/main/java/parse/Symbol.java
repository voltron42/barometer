package parse;

public interface Symbol<S,T> extends Term<S, T> {
    T accept(SymbolVisitor<S, T> visitor);
}
