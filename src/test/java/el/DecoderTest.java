package el;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class DecoderTest {

    @Test
    public void testDecodeNumber() {
        Decoder.ContextBuilder ctx = new Decoder.ContextBuilder();
        Decoder decoder = new Decoder();
        Object result = decoder.decode("${1 + 2}",ctx);
        assertEquals(3L, result);
        assertTrue(result instanceof Number);
        result = decoder.decode("${1.2 + 2.5}",ctx);
        assertEquals(3.7, result);
        assertTrue(result instanceof Number);
    }

    @Test
    public void testDecodeBool() {
        Decoder.ContextBuilder ctx = new Decoder.ContextBuilder();
        ctx.set(true,"a");
        ctx.set(false,"b");
        Decoder decoder = new Decoder();
        Object result = decoder.decode("${a && b}",ctx);
        assertEquals(false, result);
        assertTrue(result instanceof Boolean);
        result = decoder.decode("${a || b}",ctx);
        assertEquals(true, result);
        assertTrue(result instanceof Boolean);
    }

    @Test
    public void testDecodeMap() {
        Decoder.ContextBuilder ctx = new Decoder.ContextBuilder();
        ctx.set(1,"a","x");
        ctx.set(2,"a","y");
        Decoder decoder = new Decoder();
        Object result = decoder.decode("${a}",ctx);
        assertTrue(result instanceof Map);
        System.out.println(((Map) result).entrySet());
        result = decoder.decode("${a.x}",ctx);
        assertTrue(result instanceof Number);
        assertEquals(1, result);
        result = decoder.decode("${a.y}",ctx);
        assertTrue(result instanceof Number);
        assertEquals(2, result);
    }

    @Test
    public void testDecodeList() {
        List<Instance> list = new ArrayList<>();
        list.add(new Instance.Text("Hello"));
        list.add(new Instance.IntegerInstance(5L));
        Decoder.ContextBuilder ctx = new Decoder.ContextBuilder();
        ctx.set(new Instance.ListInstance(list),"list");
        System.out.println(ctx);
        Decoder decoder = new Decoder();
        Instance value = decoder.decode("${list[0]}", ctx);
        assertNotNull(value);
        assertEquals(Instance.Type.TEXT, value.getType());
    }
}