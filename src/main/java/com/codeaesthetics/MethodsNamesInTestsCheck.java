package com.codeaesthetics;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class MethodsNamesInTestsCheck extends BaseCheck {

    private static final String METHODS_NAMES_PATTERN = "methods.names.pattern";
    private static final String METHODS_NAMES_PATTERN_2 = "methods.names.pattern.2";

    @Override
    public void visitToken(DetailAST node) {
        boolean iniciaCom;
        boolean temNaPrimeiraParte;
        boolean temNaUltimaParte;

        //1. classe terminada em Test
        String className = node.findFirstToken(TokenTypes.IDENT).getText();
        String testSuffix = "Test";
        if (className.endsWith(testSuffix)) {
            DetailAST nodeCodeBlock = node.findFirstToken(TokenTypes.OBJBLOCK);
            for (DetailAST child = nodeCodeBlock.getFirstChild(); child != null; child = child.getNextSibling()) {
                if (child.getType() == TokenTypes.METHOD_DEF) {

                    // 2. nao eh um metodo privado
                    if (!isPrivateModifier(child)) {
                        String methodName = child.findFirstToken(TokenTypes.IDENT).getText();
                        
                        // 3. numero correto de separadores 
                        if (methodName.split("_").length > 3) {
                            log(child.getLineNo(), METHODS_NAMES_PATTERN_2, methodName);
                        }

                        // 4. padrao de inicio da nomenclatura (inicia com ou tem na primeira parte)
                        iniciaCom = methodName.startsWith("shouldReturn_") || methodName.startsWith("deveRetornar_");
                        temNaPrimeiraParte = methodName.contains("shouldReturn") || methodName.contains("deveRetornar");
                        
                        if (iniciaCom  || temNaPrimeiraParte) {
                            
                            // 5. tem na ultima parte
                            temNaUltimaParte = methodName.contains("_quando");
                            if (!temNaUltimaParte) {
                                log(child.getLineNo(), METHODS_NAMES_PATTERN, methodName);                
                            }
                        } else {
                            log(child.getLineNo(), METHODS_NAMES_PATTERN, methodName);
                        }
                    }
                }
            }
        }
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