package barometer.tags;

import java.util.Arrays;
import java.util.HashSet;

public class Leaf implements ASTNode {

    private String tag;

    public Leaf(String tag) {
        this.tag = tag;
    }

    @Override
    public boolean areValid(String[] tags) {
        return new HashSet<String>(Arrays.asList(tags)).contains(tag);
    }
}
