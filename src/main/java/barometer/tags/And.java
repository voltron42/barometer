package barometer.tags;

import java.util.List;

public class And extends Conjunction {
    protected And(List<ASTNode> children) {
        super(children);
    }

    @Override
    protected boolean nature() {
        return false;
    }
}
