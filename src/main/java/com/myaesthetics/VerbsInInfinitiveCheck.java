package com.myaesthetics;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class VerbsInInfinitiveCheck extends BaseCheck {

    @Override
    public void visitToken(DetailAST node) {
        DetailAST type = node.findFirstToken(TokenTypes.TYPE);
        if (type == null || type.getFirstChild() == null) {
            return;
        }
        
        String methodName = node.findFirstToken(TokenTypes.IDENT).getText();

        String regex = "[a-z]+(ar|er|ir)+[A-Z]([a-z]|[A-Z])*";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(methodName);
        boolean validName = matcher.matches();

        if (!validName) {
            log(node.getLineNo(), "infinitive.verbs.methods", methodName);
        }
    }

    @Override
    public int[] getDefaultTokens() {
        return new int[] { TokenTypes.METHOD_DEF };
    }

}