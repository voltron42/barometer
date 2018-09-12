package parse;

import java.util.ArrayList;
import java.util.List;

public abstract class TermList<S, T> {
    private List<Term<S, T>> terms;


    protected TermList(List<Term<S, T>> terms) {
        this.terms = terms;
    }

    public List<Term<S, T>> getTerms() {
        return terms;
    }

    public List<Symbol<S, T>> getSymbols() {
        List<Symbol<S,T>> out = new ArrayList<>();
        for (Term<S,T> term : terms) {
            if (term instanceof Symbol) {
                out.add((Symbol<S, T>) term);
            }
        }
        return out;
    }
}
