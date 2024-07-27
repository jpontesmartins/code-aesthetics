package com.codeaesthetics.examples;

public class ExampleForPrefixBooleanMethods {
    public boolean isSomething() { return false; }
    public boolean isActive() { return true; } //ok 
    public boolean hasSomething() { return true; } // ok
    public boolean check() { return true; } // nao ok
    public boolean verify() { return true; } // nao ok
    public String something() { return "something"; } // nao ok
}
