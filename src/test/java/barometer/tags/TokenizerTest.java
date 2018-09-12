package barometer.tags;

import org.junit.Test;

import java.text.ParseException;
import java.util.List;

import static org.junit.Assert.*;

public class TokenizerTest {

    @Test
    public void testTokenizer() throws ParseException {
        Tokenizer t = new Tokenizer();
        List<Token> tokens = t.tokenize("!TagA");
        assertEquals(2,tokens.size());
        tokens = t.tokenize("TagA & TagB");
        assertEquals(3,tokens.size());
    }
}