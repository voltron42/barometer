package xscript.parser;

import org.junit.Test;
import xscript.exec.Executor;
import xscript.model.Command;
import xscript.model.Print;
import xscript.model.Program;
import xscript.model.Prompt;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.*;

public class ProgramParserTest {

    private static ProgramParser programParser = new ProgramParser();

    @Test
    public void testParseHelloWorld() throws IOException, JAXBException {
        Executor executor = new Executor();
        String xml = new String(Files.readAllBytes(Paths.get("resources/helloworld.xml")));
        System.out.println(xml);
        Program program = programParser.parse(xml);
        System.out.println(program.getName());
        assertNull(program.getSubroutines());
        List<Command> commands = program.getMainRoutine().getCommands();
        System.out.println(commands.size());
        Print print = (Print) commands.get(0);
        System.out.println(print.getValueExpression());
        //executor.execute(program);

    }

    @Test
    public void testParseHelloYou() throws IOException, JAXBException {
        Executor executor = new Executor();
        String xml = new String(Files.readAllBytes(Paths.get("resources/helloyou.xml")));
        System.out.println(xml);
        Program program = programParser.parse(xml);
        System.out.println(program.getName());
        assertNull(program.getSubroutines());
        List<Command> commands = program.getMainRoutine().getCommands();
        System.out.println(commands.size());
        Prompt prompt = (Prompt) commands.get(0);
        System.out.println(prompt.getMessageExpression());
        System.out.println(prompt.getVariableName());
        Print print = (Print) commands.get(1);
        System.out.println(print.getValueExpression());
        //executor.execute(program);

    }
}