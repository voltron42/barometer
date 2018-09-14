package barometer.tags;

import java.util.Arrays;
import java.util.HashSet;

public enum TokenType {
    OPEN(new StringMatcher("("),false, "NOT", "AND", "OR", null),
    CLOSE(new StringMatcher(")"),true, "TAG"),
    NOT(new StringMatcher("!"),false, "AND", "OR", null),
    AND(new StringMatcher("&"),false, "CLOSE", "TAG"),
    OR(new StringMatcher("|"),false, "CLOSE", "TAG"),
    SPACE(new PatternMatcher("\\s+", 1),false,true),
    TAG(new PatternMatcher("[a-zA-Z][a-zA-Z0-9]+",2),true, "AND", "OR", "NOT", "OPEN", null);

    private final TokenMatcher matcher;
    private boolean isEnd;
    private final boolean ignore;
    private final HashSet<TokenType> prev;

    TokenType(TokenMatcher matcher, boolean isEnd, String... prev) {this(matcher, isEnd, false, prev);}
    TokenType(TokenMatcher matcher, boolean isEnd, boolean ignore, String... prev) {
        this.matcher = matcher;
        this.isEnd = isEnd;
        this.ignore = ignore;
        this.prev = new HashSet<>();
        for (String one : prev) {
            if (one == null) {
                this.prev.add(null);
            } else {
                this.prev.add(TokenType.valueOf(one));
            }
        }
    }

    public TokenMatcher getMatcher() {
        return matcher;
    }

    public boolean ignore() {
        return ignore;
    }

    public boolean isPrevValid(TokenType prev) {
        return this.prev.contains(prev);
    }

    public boolean isEnd() {
        return isEnd;
    }
}
