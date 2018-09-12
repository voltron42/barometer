package barometer.model.common;

import java.util.List;

public class If implements Command {
    private String conditionExpression;
    private ThenBlock thenBlock;
    private List<ElseIfBlock> elseIfBlocks;
    private ElseBlock elseBlock;
}
