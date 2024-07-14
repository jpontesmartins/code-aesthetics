package com.myaesthetics;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;

public class BaseCheck extends AbstractCheck {

    @Override
    public int[] getAcceptableTokens() {
        return getDefaultTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[0];
    }

    @Override
    public int[] getDefaultTokens() {
        throw new RuntimeException("BaseCheck client must implements getDefaultTokens");
    }

}
