package barometer.tags;

import java.util.List;

public class Or extends Conjunction {
    protected Or(List<ASTNode> children) {
        super(children);
    }

    @Override
    protected boolean nature() {
        return true;
    }
}
