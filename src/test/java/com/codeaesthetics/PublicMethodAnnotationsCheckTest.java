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

public class PublicMethodAnnotationsCheckTest {

    private static final String PATH_TO_EXAMPLE = "src/test/java/com/codeaesthetics/examples/";
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

        DefaultConfiguration checkConfig = new DefaultConfiguration(PublicMethodAnnotationsCheck.class.getCanonicalName());
        checkConfig.addProperty("classSuffixes", "Service,Srvc");
        checkConfig.addProperty("requiredAnnotations", "Deprecated,SuppressWarnings");
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
    public void shouldTest_LackOfAnnotationsInPublicMethods() throws Exception {
        File file = new File(PATH_TO_EXAMPLE + "ExampleService.java");
        checker.process(List.of(file));

        assertEquals(1, messages.size());
        assertEquals("o metodo 'algumMetodoOutro' requer a annotation 'Deprecated'", messages.get(0));
    }

    @Test
    public void shouldTest_SuffixClassNames() throws Exception {
        File file = new File(PATH_TO_EXAMPLE + "ExampleSrvc.java");
        checker.process(List.of(file));

        assertEquals(1, messages.size());
        assertEquals("o metodo 'algumMetodoOutro' requer a annotation 'Deprecated'", messages.get(0));
    }

    @Test
    public void shouldTest_SuffixClassName() throws Exception {
        File file = new File(PATH_TO_EXAMPLE + "ExampleRepository.java");
        checker.process(List.of(file));

        assertEquals(0, messages.size());
    }


}
