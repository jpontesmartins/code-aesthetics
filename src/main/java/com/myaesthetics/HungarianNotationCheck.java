package com.myaesthetics;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class HungarianNotationCheck extends BaseCheck {

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
            boolean secondLetterIsUpperCase = variableName.length() > 1 && Character.isUpperCase(variableName.charAt(1));

            if (variableName.startsWith(INT_TYPE) || (variableName.startsWith(I_LOWER_case) && secondLetterIsUpperCase)) {
                log(node.getLineNo(), "variable.name.prefix", variableName);
            }
        }

        if (FLOAT_TYPE.equals(type.getFirstChild().getText())) {
            String variableName = node.findFirstToken(TokenTypes.IDENT).getText();
            boolean secondLetterIsUpperCase = variableName.length() > 1 && Character.isUpperCase(variableName.charAt(1));
            if (variableName.startsWith(F_LOWER_CASE) || (variableName.startsWith(F_LOWER_CASE) && secondLetterIsUpperCase)) {
                log(node.getLineNo(), "variable.name.prefix", variableName);
            }
        }
        
        if (DOUBLE_TYPE.equals(type.getFirstChild().getText())) {
            String variableName = node.findFirstToken(TokenTypes.IDENT).getText();
            boolean secondLetterIsUpperCase = variableName.length() > 1 && Character.isUpperCase(variableName.charAt(1));
            if (variableName.startsWith(D_LOWER_CASE) || (variableName.startsWith(D_LOWER_CASE) && secondLetterIsUpperCase)) {
                log(node.getLineNo(), "variable.name.prefix", variableName);
            }
        }
    }

    private void checkVariableNameForString(DetailAST ast, DetailAST typeAST) {
        if (typeAST != null && STRING_TYPE.equals(typeAST.getFirstChild().getText())) {

            DetailAST identifierAST = ast.findFirstToken(TokenTypes.IDENT);
            String variableName = identifierAST.getText();
            if (variableName.startsWith(STR) || variableName.startsWith(S_LOWER_CASE)) {
                log(ast.getLineNo(), "variable.name.prefix", variableName);
            }
        }
    }

    @Override
    public int[] getDefaultTokens() {
        return new int[] { TokenTypes.VARIABLE_DEF };
    }
    
}