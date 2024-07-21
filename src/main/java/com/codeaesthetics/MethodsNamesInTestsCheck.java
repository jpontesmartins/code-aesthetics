package com.codeaesthetics;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class MethodsNamesInTestsCheck extends BaseCheck {

    private static final String METHODS_NAMES_PATTERN = "methods.names.pattern";
    private boolean iniciaCom;
    private boolean temNaPrimeiraParte;
    private boolean temNaUltimaParte;

    @Override
    public void visitToken(DetailAST node) {
        DetailAST type = node.findFirstToken(TokenTypes.TYPE);
        if (type == null || type.getFirstChild() == null) {
            return;
        }

        // methods names in tests check
        // TODO: 1. classname terminada em Test
        // TODO: 2. tem annotation Test no método
        
        
        String methodName = node.findFirstToken(TokenTypes.IDENT).getText();
        iniciaCom = methodName.startsWith("shouldReturn_") || methodName.startsWith("deveRetornar_");
        temNaPrimeiraParte = methodName.contains("shouldReturn") || methodName.contains("deveRetornar");
        if (iniciaCom  || temNaPrimeiraParte) {
            temNaUltimaParte = methodName.contains("_quando");
            if (!temNaUltimaParte) {
                log(node.getLineNo(), METHODS_NAMES_PATTERN, methodName);                
            }
        } else {
            log(node.getLineNo(), METHODS_NAMES_PATTERN, methodName);
        }

    }

    @Override
    public int[] getDefaultTokens() {
        return new int[] { TokenTypes.METHOD_DEF };
    }

}