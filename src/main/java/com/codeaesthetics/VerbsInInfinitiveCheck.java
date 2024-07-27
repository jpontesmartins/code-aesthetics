package com.codeaesthetics;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class VerbsInInfinitiveCheck extends BaseCheck {

    private static final String INFINITIVE_VERBS_METHODS = "infinitive.verbs.methods";

    @Override
    public void visitToken(DetailAST node) {
        String methodName = node.findFirstToken(TokenTypes.IDENT).getText();

        String regex = "[a-z]+(ar|er|ir)+[A-Z]([a-z]|[A-Z])*";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(methodName);
        boolean validName = matcher.matches();

        if (!validName) {
            log(node.getLineNo(), INFINITIVE_VERBS_METHODS, methodName);
        }
    }

    @Override
    public int[] getDefaultTokens() {
        return new int[] { TokenTypes.METHOD_DEF };
    }

}