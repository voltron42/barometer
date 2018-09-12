package barometer.tags;

import java.util.List;

public abstract class Conjunction implements ASTNode {

    private List<ASTNode> children;

    protected Conjunction(List<ASTNode> children) {
        this.children = children;
    }

    protected abstract boolean nature();

    @Override
    public boolean areValid(String[] tags) {
        boolean rule = nature();
        for (ASTNode child : children) {
            if (rule == child.areValid(tags)) {
                return rule;
            }
        }
        return !rule;
    }
}
