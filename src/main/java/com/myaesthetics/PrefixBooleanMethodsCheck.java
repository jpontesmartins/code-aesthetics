package com.myaesthetics;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class PrefixBooleanMethodsCheck extends AbstractCheck {

    private static final String MESSAGE_PROPERTY_BOOLEAN_METHOD_NAME = "boolean.method.name";
    private static final String BOOLEAN = "boolean";
    private static final String HAS = "has";
    private static final String IS = "is";

    @Override
    public void visitToken(DetailAST ast) {
        DetailAST typeAST = ast.findFirstToken(TokenTypes.TYPE);
        if (typeAST == null || typeAST.getFirstChild() == null) {
            return;
        }

        // boolean
        String returnType = typeAST.getFirstChild().getText();
        if (BOOLEAN.equals(returnType)) {
            DetailAST nameAST = ast.findFirstToken(TokenTypes.IDENT);
            String methodName = nameAST.getText();

            // is ou has
            if (!(methodName.startsWith(IS) || methodName.startsWith(HAS))) {
                log(ast.getLineNo(), MESSAGE_PROPERTY_BOOLEAN_METHOD_NAME, methodName);
            }
        }
    }
    
    @Override
    public int[] getDefaultTokens() {
        return new int[] { TokenTypes.METHOD_DEF };
    }

    @Override
    public int[] getAcceptableTokens() {
        return getDefaultTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[0];
    }
}