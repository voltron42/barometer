package parse;

import java.util.Map;

public interface Parser<S> {
    S parse(String expression);

    class Factory<U,S extends Enum<S>> {
        Parser<U> build(SymbolVisitor<S, U> visitor, Map<S,Symbol<S,U>> rules, S root) {
            return new Parser<U>() {
                @Override
                public U parse(String expression) {
                    return null;
                }
            };
        }
    }
}
