package barometer.tags;

import org.junit.Test;

import static org.junit.Assert.*;

public class TokenTypeTest {

    @Test
    public void getMatcher() {
        assertFalse("!Tag".matches("[a-zA-Z][a-zA-Z0-9]+"));
        assertTrue("Tag".matches("[a-zA-Z][a-zA-Z0-9]+"));
        assertFalse("Tag&".matches("[a-zA-Z][a-zA-Z0-9]+"));

        assertEquals("Tag",TokenType.TAG.getMatcher().matchFrom("Tag",0));
    }
}