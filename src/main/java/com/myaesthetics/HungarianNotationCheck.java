package com.myaesthetics;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class HungarianNotationCheck extends AbstractCheck {

    private static final String STRING_TYPE = "String";
    private static final String INT_TYPE = "int";
    private static final String STR = "str";
    private static final String S = "s";

    @Override
    public void visitToken(DetailAST ast) {
        DetailAST typeAST = ast.findFirstToken(TokenTypes.TYPE);

        // String
        if (typeAST != null && STRING_TYPE.equals(typeAST.getFirstChild().getText())) {
            // Get the variable name
            DetailAST identifierAST = ast.findFirstToken(TokenTypes.IDENT);
            String variableName = identifierAST.getText();
            if (variableName.startsWith(STR) || variableName.startsWith(S)) {
                log(ast.getLineNo(), "string.variable.name.prefix", variableName);
            }
        }

        // int
        if (typeAST != null && INT_TYPE.equals(typeAST.getFirstChild().getText())) {
            DetailAST identifierAST = ast.findFirstToken(TokenTypes.IDENT);
            String variableName = identifierAST.getText();
            if (variableName.startsWith("int") ||
                    (variableName.startsWith("i") && variableName.length() > 1
                            && Character.isUpperCase(variableName.charAt(1)))) {
                log(ast.getLineNo(), "string.variable.name.prefix", variableName);
            }
        }

        // float
        if (typeAST != null && "float".equals(typeAST.getFirstChild().getText())) {
            DetailAST identifierAST = ast.findFirstToken(TokenTypes.IDENT);
            String variableName = identifierAST.getText();
            if (variableName.startsWith("f") ||
                    (variableName.startsWith("f") &&
                            variableName.length() > 1 &&
                            Character.isUpperCase(variableName.charAt(1)))) {
                log(ast.getLineNo(), "string.variable.name.prefix", variableName);
            }
        }
        
        // double
        if (typeAST != null && "double".equals(typeAST.getFirstChild().getText())) {
            DetailAST identifierAST = ast.findFirstToken(TokenTypes.IDENT);
            String variableName = identifierAST.getText();
            if (variableName.startsWith("d") ||
                    (variableName.startsWith("d") &&
                            variableName.length() > 1 &&
                            Character.isUpperCase(variableName.charAt(1)))) {
                log(ast.getLineNo(), "string.variable.name.prefix", variableName);
            }
        }
    }

    @Override
    public int[] getDefaultTokens() {
        return new int[] { TokenTypes.VARIABLE_DEF };
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