package com.codeaesthetics;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AuditListener;

public class MethodsNamesInTestsCheckTest {
  
  
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

        DefaultConfiguration checkConfig = new DefaultConfiguration(MethodsNamesInTestsCheck.class.getCanonicalName());
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
    public void shouldReturnMethodsNames() throws Exception {
        File file = new File(PATH_TO_EXAMPLES + "ExampleForMethodsNamesInTests.java");
        checker.process(List.of(file));
        
        String expectedMessage = "O metodo '%s' deve estar de acordo com a nomenclatura para testes";

        assertEquals(2, messages.size());
        assertEquals(String.format(expectedMessage, "shouldReturn_mensagemErroquandoSucesso"), messages.get(0));
        assertEquals(String.format(expectedMessage, "deveRetornarMensagemOkQuandoStatus200"), messages.get(1));

    }
}
