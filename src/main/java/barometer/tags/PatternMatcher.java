package barometer.tags;

public class PatternMatcher implements TokenMatcher {
    private final String pattern;
    private int minSize;

    public PatternMatcher(String pattern, int minSize) {
        this.pattern = pattern;
        this.minSize = minSize;
    }

    @Override
    public String matchFrom(String input, int index) {
        int end = index + minSize;
        String out = "";
        while (end <= input.length() && input.substring(index,end).matches(pattern)) {
            end++;
        }
        out = input.substring(index,end-1);
        System.out.println(out);
        System.out.println(out.length());
        if (out.matches(pattern)) {
            return out;
        } else {
            return null;
        }
    }
}
