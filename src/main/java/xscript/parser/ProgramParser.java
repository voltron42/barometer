package xscript.parser;

import xscript.model.Program;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;

public class ProgramParser {

    public Program parse(String xml) throws JAXBException {
        JAXBContext ctx = JAXBContext.newInstance(Program.class);

        Unmarshaller unmarshaller = ctx.createUnmarshaller();
        return unmarshaller.unmarshal(new StreamSource(new ByteArrayInputStream(xml.getBytes())), Program.class).getValue();
    }
}
