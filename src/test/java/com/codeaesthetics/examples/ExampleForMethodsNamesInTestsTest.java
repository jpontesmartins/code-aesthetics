package com.codeaesthetics.examples;

import org.junit.jupiter.api.Test;

public class ExampleForMethodsNamesInTestsTest {
  @Test
  public void shouldReturn_mensagemErro_quandoParametroInvalido() { }  // ok
  @Test
  public void salvar_deveRetornarMensagemErro_quandoParametroInvalido() { } // ok
  @Test
  public void shouldReturn_mensagemOk_quandoSucesso() { } // ok
  @Test
  protected void shouldReturn_mensagemErroquandoSucesso() { } // nao ok
  @Test
  public void deveRetornar_mensagemOk_quando200() { } // ok
  @Test
  void salvarEntidade_deveRetornarMensagemSucesso_quandoUsuarioAdmin() { } // ok
  @Test
  void deveRetornarMensagemErro_quandoUsuarioNaoAutorizado() { } // ok
  @Test
  void deveRetornarMensagemOkQuandoStatus200() { } // nao ok
  @Test
  void deveRetornar_mensagemOk_quandoAutorizado_200() { } // nao ok
}
