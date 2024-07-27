package com.codeaesthetics;

import com.puppycrawl.tools.checkstyle.api.*;
import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HungarianNotationCheckTest {

    private static final String PATH_TO_EXAMPLES = "src/test/java/com/codeaesthetics/examples/";
    private Checker checker;
    private List<String> messages;

    @BeforeEach
    public void setUp() throws Exception {
        Locale.setDefault(Locale.ENGLISH);

        checker = new Checker();
        checker.setModuleClassLoader(Thread.currentThread().getContextClassLoader());

        DefaultConfiguration config = new DefaultConfiguration("Checker");
        DefaultConfiguration treeWalkerConfig = new DefaultConfiguration("TreeWalker");
        config.addChild(treeWalkerConfig);

        DefaultConfiguration checkConfig = new DefaultConfiguration(HungarianNotationCheck.class.getCanonicalName());
        treeWalkerConfig.addChild(checkConfig);

        checker.configure(config);

        messages = new ArrayList<>();
        checker.addListener(new AuditListener() {
            @Override
            public void auditStarted(AuditEvent evt) {
            }

            @Override
            public void auditFinished(AuditEvent evt) {
            }

            @Override
            public void fileStarted(AuditEvent evt) {
            }

            @Override
            public void fileFinished(AuditEvent evt) {
            }

            @Override
            public void addError(AuditEvent evt) {
              messages.add(evt.getViolation().getViolation());
            }

            @Override
            public void addException(AuditEvent evt, Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }

    @Test
    public void shouldReturn_numbersOfErrors() throws Exception {
        File file = new File(PATH_TO_EXAMPLES + "ExampleForHungarianNotation.java");
        checker.process(List.of(file));
        
        assertEquals(8, messages.size());
    }


    @Test
    public void shouldReturn_StringValues() throws Exception {
        File file = new File(PATH_TO_EXAMPLES + "ExampleForHungarianNotation.java");
        checker.process(List.of(file));
        
        // String
        String expectedMessage_strNome = "Remova a notacao hungara em 'strNome'.";
        String expectedMessage_sNome = "Remova a notacao hungara em 'sNome'.";

        assertEquals(expectedMessage_strNome, messages.get(0));
        assertEquals(expectedMessage_sNome, messages.get(1));

    }

    @Test
    public void shouldReturn_intAndIntegerValues_() throws Exception {
        File file = new File(PATH_TO_EXAMPLES + "ExampleForHungarianNotation.java");
        checker.process(List.of(file));
        
        // int
        String expectedMessage_iContador = "Remova a notacao hungara em 'iContador'.";
        String expectedMessage_intContador = "Remova a notacao hungara em 'intContador'.";
        String expectedMessage_iAlgumaCoisa = "Remova a notacao hungara em 'iAlgumaCoisa'.";
        String expectedMessage_intAlgumaCoisa = "Remova a notacao hungara em 'intAlgumaCoisa'.";

        assertEquals(expectedMessage_iContador, messages.get(2));
        assertEquals(expectedMessage_intContador, messages.get(3));
        assertEquals(expectedMessage_iAlgumaCoisa, messages.get(4));
        assertEquals(expectedMessage_intAlgumaCoisa, messages.get(5));

    }

    @Test
    public void shouldReturn_floatAndDoubleValues_() throws Exception {
        File file = new File(PATH_TO_EXAMPLES + "ExampleForHungarianNotation.java");
        checker.process(List.of(file));
        
        // float e double
        String expectedMessage_fVar = "Remova a notacao hungara em 'fVar'.";
        String expectedMessage_dVar = "Remova a notacao hungara em 'dVar'.";

        assertEquals(expectedMessage_fVar, messages.get(6));
        assertEquals(expectedMessage_dVar, messages.get(7));
    }

}
