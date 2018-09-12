package parse;

public class Literal<S, T> implements Term<S, T> {
    private String term;

    public Literal(String term) {
        this.term = term;
    }

    public String getTerm() {
        return term;
    }
}
