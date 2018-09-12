package barometer.tags;

public class Not implements ASTNode {

    private ASTNode child;

    public Not(ASTNode child) {
        this.child = child;
    }

    @Override
    public boolean areValid(String[] tags) {
        return !child.areValid(tags);
    }
}
