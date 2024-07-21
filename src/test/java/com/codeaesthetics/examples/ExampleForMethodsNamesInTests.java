package com.codeaesthetics.examples;

public class ExampleForMethodsNamesInTests {
  void shouldReturn_mensagemErro_quandoParametroInvalido() { }  // ok
  void salvar_deveRetornarMensagemErro_quandoParametroInvalido() { } // ok
  void shouldReturn_mensagemOk_quandoSucesso() { } // ok
  void shouldReturn_mensagemErroquandoSucesso() { } // nao-ok
  void deveRetornar_mensagemOk_quando200() { } // ok
  void salvarEntidade_deveRetornarMensagemSucesso_quandoUsuarioAdmin() { } // ok
  void deveRetornarMensagemErro_quandoUsuarioNaoAutorizado() { } // ok
  void deveRetornarMensagemOkQuandoStatus200() { } // nao-ok
}
