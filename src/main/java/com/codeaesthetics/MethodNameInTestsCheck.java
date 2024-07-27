package com.codeaesthetics;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class MethodNameInTestsCheck extends BaseCheck {

    private static final String METHODS_NAMES_PATTERN = "method.name.pattern";
    private static final String METHODS_NAMES_PATTERN_2 = "method.name.pattern.2";
    private static final String METHODS_NAMES_PATTERN_3 = "method.name.pattern.3";

    
    //deve ter o numero correto de separadores _
    private static String TEST_SUFFIX = "Test";

    private Set<String> padroesIniciais = new HashSet<>();
    private Set<String> padroesFinais = new HashSet<>();

    @Override
    public void visitToken(DetailAST node) {
        //1. classe terminada em Test
        String className = node.findFirstToken(TokenTypes.IDENT).getText();
        if (!className.endsWith(TEST_SUFFIX)) {
            return;
        }

        DetailAST nodeCodeBlock = node.findFirstToken(TokenTypes.OBJBLOCK);
        for (DetailAST child = nodeCodeBlock.getFirstChild(); child != null; child = child.getNextSibling()) {
            if (child.getType() == TokenTypes.METHOD_DEF) {

                // 2. nao eh um metodo privado
                if (isPrivateModifier(child)) {
                    return;
                }
                
                String methodName = child.findFirstToken(TokenTypes.IDENT).getText();

                if (Character.isUpperCase(methodName.charAt(0))) {
                    log(child.getLineNo(), METHODS_NAMES_PATTERN_3, methodName);
                }

                // 3. numero correto de separadores 
                if (methodName.split("_").length > 3) {
                    log(child.getLineNo(), METHODS_NAMES_PATTERN_2, methodName);
                }

                // 4. padrao de inicio da nomenclatura (inicia com ou tem na primeira parte)
                if (padraoExisteNaPrimeiraParte(methodName)) {
                    // 5. tem na ultima parte
                    if (!padraoExisteNaUltimaParte(methodName)) {
                        log(child.getLineNo(), METHODS_NAMES_PATTERN, methodName);                
                    }
                } else {
                    log(child.getLineNo(), METHODS_NAMES_PATTERN, methodName);
                }
            }
        }
    }

    public void setPadroesIniciais(String[] padroesIniciais) {
        this.padroesIniciais = new HashSet<>(Arrays.asList(padroesIniciais));
    }

    public void setPadroesFinais(String[] padroesFinais) {
        this.padroesFinais = new HashSet<>(Arrays.asList(padroesFinais));
    }
    
    private boolean padraoExisteNaUltimaParte(String methodName) {
        return padroesFinais.stream().anyMatch(methodName::contains);
    }

    private boolean padraoExisteNaPrimeiraParte(String methodName) {
        return padroesIniciais.stream().anyMatch(methodName::contains);
    }

    private boolean isPrivateModifier(DetailAST node) {
        DetailAST nodeModifier = node.findFirstToken(TokenTypes.MODIFIERS);
        for (DetailAST modifier = nodeModifier.getFirstChild(); modifier != null; modifier = modifier.getNextSibling()) {
            if (modifier.getType() == TokenTypes.LITERAL_PRIVATE) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int[] getDefaultTokens() {
        return new int[] { TokenTypes.CLASS_DEF };
    }

}