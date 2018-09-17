package el2;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DecoderTest {
    @Test
    public void decode() throws Exception {
    }

    @Test
    public void addFunction() throws Exception {
    }

    @Test
    public void set() throws Exception {
    }

    @Test
    public void testDecodeList() {
        List<Instance> list = new ArrayList<>();
        list.add(new Instance.Text("Hello"));
        list.add(new Instance.IntegerInstance(5L));
        Decoder decoder = new Decoder();
        decoder.set("list", new Instance.ListInstance(list));
        Instance value = decoder.decode("${list[0]}");
        assertNotNull(value);
        assertEquals(Instance.Type.TEXT, value.getType());
        System.out.println(value.stringValue());
    }

}