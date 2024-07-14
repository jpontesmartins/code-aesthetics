package com.myaesthetics;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Valida se o nome da variável está usando um prefixo para indicar seu tipo.
 * Ex: int iContador = 0;
 * Usar o i para indicar um inteiro, um d para um double, str para String, etc.
 */
public class HungarianNotationCheck extends BaseCheck {

    private static final String VARIABLE_NAME_PREFIX = "variable.name.prefix";
    private static final String D_LOWER_CASE = "d";
    private static final String F_LOWER_CASE = "f";
    private static final String I_LOWER_case = "i";
    private static final String S_LOWER_CASE = "s";
    private static final String STR = "str";
    private static final String INT_TYPE = "int";
    private static final String DOUBLE_TYPE = "double";
    private static final String FLOAT_TYPE = "float";
    private static final String STRING_TYPE = "String";

    @Override
    public void visitToken(DetailAST node) {
        DetailAST type = node.findFirstToken(TokenTypes.TYPE);

        checkVariableNameForString(node, type);
        checkVariableNameForNumbers(node, type);
    }

    private void checkVariableNameForNumbers(DetailAST node, DetailAST type) {
        if (type == null) return;

        if (INT_TYPE.equals(type.getFirstChild().getText())) {
            String variableName = node.findFirstToken(TokenTypes.IDENT).getText();

            if (variableName.startsWith(INT_TYPE) || (variableName.startsWith(I_LOWER_case) 
                && isSecondLetterUpperCase(variableName))) {
                log(node.getLineNo(), VARIABLE_NAME_PREFIX, variableName);
            }
        }

        if (FLOAT_TYPE.equals(type.getFirstChild().getText())) {
            String variableName = node.findFirstToken(TokenTypes.IDENT).getText();
            if (variableName.startsWith(F_LOWER_CASE) || (variableName.startsWith(F_LOWER_CASE) 
                && isSecondLetterUpperCase(variableName))) {
                log(node.getLineNo(), VARIABLE_NAME_PREFIX, variableName);
            }
        }
        
        if (DOUBLE_TYPE.equals(type.getFirstChild().getText())) {
            String variableName = node.findFirstToken(TokenTypes.IDENT).getText();
            if (variableName.startsWith(D_LOWER_CASE) || (variableName.startsWith(D_LOWER_CASE) 
                && isSecondLetterUpperCase(variableName))) {
                log(node.getLineNo(), VARIABLE_NAME_PREFIX, variableName);
            }
        }
    }
   
    private void checkVariableNameForString(DetailAST node, DetailAST type) {
        if (type != null && STRING_TYPE.equals(type.getFirstChild().getText())) {

            DetailAST nodeIdentifier = node.findFirstToken(TokenTypes.IDENT);
            String variableName = nodeIdentifier.getText();
            if (variableName.startsWith(STR) || variableName.startsWith(S_LOWER_CASE)) {
                log(node.getLineNo(), VARIABLE_NAME_PREFIX, variableName);
            }
        }
    }

    private boolean isSecondLetterUpperCase(String variableName) {
        return variableName.length() > 1 && Character.isUpperCase(variableName.charAt(1));
    }

    @Override
    public int[] getDefaultTokens() {
        return new int[] { TokenTypes.VARIABLE_DEF };
    }
    
}