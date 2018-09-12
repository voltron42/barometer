package barometer.tags;

public class StringMatcher implements TokenMatcher {
    private String string;

    public StringMatcher(String string) {
        this.string = string;
    }

    @Override
    public String matchFrom(String input, int index) {
        if (input.substring(index).startsWith(string)) {
            return string;
        } else {
            return null;
        }
    }
}
