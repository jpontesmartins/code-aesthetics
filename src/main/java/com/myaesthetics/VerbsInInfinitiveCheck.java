package com.myaesthetics;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class VerbsInInfinitiveCheck extends AbstractCheck {

    @Override
    public void visitToken(DetailAST ast) {
        DetailAST typeAST = ast.findFirstToken(TokenTypes.TYPE);
        if (typeAST == null || typeAST.getFirstChild() == null) {
            return;
        }
        
        DetailAST nameAST = ast.findFirstToken(TokenTypes.IDENT);
        String methodName = nameAST.getText();

        String regex = "[a-z]+(ar|er|ir)+[A-Z]([a-z]|[A-Z])*";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(methodName);
        boolean nomeValido = matcher.matches();

        if (!nomeValido) {
            log(ast.getLineNo(), "infinitive.verbs.methods", methodName);
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