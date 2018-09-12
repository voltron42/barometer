package parse;

public interface SymbolVisitor<S, T> {

    T visit(Terminal terminal);

    T visit(NamedSymbol<S, T> namedSymbol);

    T visit(ZeroToMany<S, T> zeroToMany);

    T visit(OneToMany<S, T> oneToMany);

    T visit(Choice<S, T> choice);

    T visit(Optional<S, T> optional);

    T visit(Sequence<S, T> sequence);
}
