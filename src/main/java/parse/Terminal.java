package parse;

import java.util.regex.Pattern;

public class Terminal<S, T> implements Symbol<S, T> {

    private Pattern pattern;

    public Terminal(Pattern pattern) {
        this.pattern = pattern;
    }

    public Pattern getPattern() {
        return pattern;
    }

    @Override
    public T accept(SymbolVisitor<S, T> visitor) {
        return visitor.visit(this);
    }
}
