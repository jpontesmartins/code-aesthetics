package com.myaesthetics;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class PrefixBooleanMethodsCheck extends BaseCheck {

    private static final String MESSAGE_PROPERTY_BOOLEAN_METHOD_NAME = "boolean.method.name";
    private static final String BOOLEAN = "boolean";
    private static final String HAS = "has";
    private static final String IS = "is";

    @Override
    public void visitToken(DetailAST ast) {
        DetailAST type = ast.findFirstToken(TokenTypes.TYPE);
        if (type == null || type.getFirstChild() == null) {
            return;
        }
        if (BOOLEAN.equals(type.getFirstChild().getText())) {
            String methodName = ast.findFirstToken(TokenTypes.IDENT).getText();

            if (!(methodName.startsWith(IS) || methodName.startsWith(HAS))) {
                log(ast.getLineNo(), MESSAGE_PROPERTY_BOOLEAN_METHOD_NAME, methodName);
            }
        }
    }
    
    @Override
    public int[] getDefaultTokens() {
        return new int[] { TokenTypes.METHOD_DEF };
    }

}