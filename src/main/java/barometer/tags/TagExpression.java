package barometer.tags;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public interface TagExpression {
    boolean areValid(String[] tags);

    class Parser {

        private static final Tokenizer tokenizer = new Tokenizer();

        private static final TagExpression emptyExpression = new TagExpression() {
            @Override
            public boolean areValid(String[] tags) {
                return true;
            }
        };

        private static class Position{
            private String expr;
            private List<Token> tokens;
            private int index = 0;

            private Position(String expr, List<Token> tokens) {
                this.expr = expr;
                this.tokens = tokens;
            }

            boolean hasNext() {
                return tokens.size() > index + 1;
            }

            Token next() {
                return this.tokens.get(index++);
            }

            int getErrorIndex() {
                int i = 0;
                for (int x = 0; x < index; x++) {
                    i += tokens.get(x).getValue().length();
                }
                return i;
            }
        }

        private ASTNode parse(Position pos, int depth) throws ParseException {
            List<ASTNode> nodeList = new ArrayList<>();
            TokenType not = null;
            TokenType conj = null;
            TokenType prev = null;
            while(pos.hasNext()) {
                Token token = pos.next();
                TokenType type = token.getType();
                if (type.isPrevValid(prev))
                    throw new BadFollowException(pos);
                prev = type;
                switch (type) {
                    case CLOSE:
                        break;
                    case NOT:
                        if (not != null) {
                            throw new DoubleNegException(pos);
                        }
                        not = TokenType.NOT;
                        break;
                    case TAG:
                        if (TokenType.NOT.equals(not)) {
                            nodeList.add(new Not(new Leaf(token.getValue())));
                        } else {
                            nodeList.add(new Leaf(token.getValue()));
                        }
                        not = null;
                        break;
                    case AND:
                    case OR:
                        if (conj != null && conj != type)
                            throw new ConfusedConjunctionException(pos);
                        conj = type;
                        break;
                    case OPEN:
                        if (TokenType.NOT.equals(not)) {
                            nodeList.add(new Not(parse(pos,depth + 1)));
                        } else {
                            nodeList.add(parse(pos,depth + 1));
                        }
                        not = null;
                        break;
                    default:
                }
                if (TokenType.CLOSE.equals(type)) {
                    break;
                }
                if (!pos.hasNext() && !type.isEnd()) {
                    throw new IncompleteExpressionException(pos);
                }
            }
            if (!pos.hasNext() && depth > 0) {
                throw new UnclosedParenException(pos);
            }
            if (nodeList.size() == 1 && conj == null) {
                return nodeList.get(0);
            }
            if (TokenType.AND.equals(conj) && nodeList.size() > 1) {
                return new And(nodeList);
            }
            if (TokenType.OR.equals(conj) && nodeList.size() > 1) {
                return new Or(nodeList);
            }
            throw new IncompleteConjunctionException(pos);
        }

        public TagExpression parse(String expr) throws ParseException {
            List<Token> tokens = tokenizer.tokenize(expr);
            if (tokens.isEmpty()) {
                return emptyExpression;
            } else {
                return new TagTree(parse(new Position(expr, tokens),0));
            }
        }

        private class TagTree implements TagExpression {
            private ASTNode root;

            public TagTree(ASTNode root) {
                this.root = root;
            }

            @Override
            public boolean areValid(String[] tags) {
                return root.areValid(tags);
            }
        }

        private class DoubleNegException extends ParseException {
            public DoubleNegException(Position pos) {
                super("Double Negative found in expression: " + pos.expr, pos.getErrorIndex());
            }
        }

        private class BadFollowException extends ParseException {
            public BadFollowException(Position pos) {
                super("Improper element following: " + pos.expr, pos.getErrorIndex());
            }
        }

        private class UnclosedParenException extends ParseException {
            public UnclosedParenException(Position pos) {
                super("Unclosed Parenthesis: " + pos.expr, pos.getErrorIndex());
            }
        }

        private class ConfusedConjunctionException extends ParseException {
            public ConfusedConjunctionException(Position pos) {
                super("Cannot have AND and OR at the same level; please use parenthesis to distinguish: " + pos.expr, pos.getErrorIndex());
            }
        }

        private class IncompleteExpressionException extends ParseException {
            public IncompleteExpressionException(Position pos) {
                super("Expression incomplete: " + pos.expr, pos.getErrorIndex());
            }
        }

        private class IncompleteConjunctionException extends ParseException {
            public IncompleteConjunctionException(Position pos) {
                super("Conjunction incomplete: " + pos.expr, pos.getErrorIndex());
            }
        }
    }
}
