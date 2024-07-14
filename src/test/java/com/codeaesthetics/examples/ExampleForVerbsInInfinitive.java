package com.codeaesthetics.examples;

public class ExampleForVerbsInInfinitive {
    // ar
    public void clonarObjeto() { } // ok
    public void clonaObjeto() { } // nao ok
    // ir
    public void inserirRegistro() { } // ok
    public void insereRegistro() { } // nao ok
    // er
    public void removerRegistro() { } //ok
    public void removeRegistro() { } // nao ok
    // ar
    public String clonarRegistroOutro() { return ""; } // ok
    public String clonaRegistroOutro() { return ""; } // nao ok
    // ir
    public int inserirRegistroOutro() { return 0; }  // ok
    public int insereRegistroOutro() { return 0; } // nao ok

    public String nome; // ok
}
