package com.myaesthetics;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class PrefixBooleanMethodsCheck extends BaseCheck {

    private static final String MESSAGE_PROPERTY_BOOLEAN_METHOD_NAME = "boolean.method.name";
    private static final String BOOLEAN_TYPE = "boolean";
    private static final String HAS = "has";
    private static final String IS = "is";

    @Override
    public void visitToken(DetailAST node) {
        DetailAST type = node.findFirstToken(TokenTypes.TYPE);
        if (type == null || type.getFirstChild() == null) {
            return;
        }

        if (BOOLEAN_TYPE.equals(type.getFirstChild().getText())) {
            String methodName = node.findFirstToken(TokenTypes.IDENT).getText();

            if (!(methodName.startsWith(IS) || methodName.startsWith(HAS))) {
                log(node.getLineNo(), MESSAGE_PROPERTY_BOOLEAN_METHOD_NAME, methodName);
            }
        }
    }
    
    @Override
    public int[] getDefaultTokens() {
        return new int[] { TokenTypes.METHOD_DEF };
    }

}