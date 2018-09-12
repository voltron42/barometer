package barometer.tags;

import java.util.Arrays;
import java.util.HashSet;

public enum TokenType {
    OPEN(new StringMatcher("("),false,TokenType.NOT, TokenType.AND, TokenType.OR,null),
    CLOSE(new StringMatcher(")"),true,TokenType.TAG),
    NOT(new StringMatcher("!"),false,TokenType.AND, TokenType.OR,null),
    AND(new StringMatcher("&"),false,TokenType.CLOSE,TokenType.TAG),
    OR(new StringMatcher("|"),false,TokenType.CLOSE,TokenType.TAG),
    SPACE(new PatternMatcher("\\s+", 1),false,true),
    TAG(new PatternMatcher("[a-zA-Z][a-zA-Z0-9]+",2),true,TokenType.AND, TokenType.OR,TokenType.NOT,TokenType.OPEN, null);

    private final TokenMatcher matcher;
    private boolean isEnd;
    private final boolean ignore;
    private final HashSet<TokenType> prev;

    TokenType(TokenMatcher matcher, boolean isEnd, TokenType... prev) {this(matcher, isEnd, false, prev);}
    TokenType(TokenMatcher matcher, boolean isEnd, boolean ignore, TokenType... prev) {
        this.matcher = matcher;
        this.isEnd = isEnd;
        this.ignore = ignore;
        this.prev = new HashSet<>(Arrays.asList(prev));
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
