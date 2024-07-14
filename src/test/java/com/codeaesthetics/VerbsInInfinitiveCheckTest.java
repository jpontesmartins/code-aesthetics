package com.codeaesthetics;

import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AuditListener;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VerbsInInfinitiveCheckTest {

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

        DefaultConfiguration checkConfig = new DefaultConfiguration(VerbsInInfinitiveCheck.class.getCanonicalName());
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
    public void shouldValidateMethodsWithVerbsInInfinitive() throws Exception {
        File file = new File(PATH_TO_EXAMPLES + "ExampleForVerbsInInfinitive.java");
        checker.process(List.of(file));
        String expectedMessage = "O metodo '%s' deve conter um verbo no infinitivo (terminado em ar, er, ir).";

        String expectedMessageAR = String.format(expectedMessage, "clonaObjeto");
        String expectedMessageER = String.format(expectedMessage, "insereRegistro");
        String expectedMessageIR = String.format(expectedMessage, "removeRegistro");
        String expectedMessageAR2 = String.format(expectedMessage, "clonaRegistroOutro");
        String expectedMessageIR2 = String.format(expectedMessage, "insereRegistroOutro");

        assertEquals(5, messages.size());
        assertEquals(expectedMessageAR, messages.get(0));
        assertEquals(expectedMessageER, messages.get(1));
        assertEquals(expectedMessageIR, messages.get(2));
        assertEquals(expectedMessageAR2, messages.get(3));
        assertEquals(expectedMessageIR2, messages.get(4));

    }
}
