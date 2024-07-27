code-aesthetics
---
Dependência para usar junto com o `checkstyle` plugin para validar as nomenclaturas do código


## Exemplos

Padrão de nomes de métodos nas classes de teste
  ```
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
  ```

checkstyle.xml
  ```
    <module name="com.codeaesthetics.MethodsNamesInTestsCheck" />
  ```


Verbos nos Infinitivo (AR, ER, IR)
  ```
    public class ExampleForVerbsInInfinitive {
      public void clonarObjeto() { } // ok
      public void clonaObjeto() { } // nao ok
      public void inserirRegistro() { } // ok
      public void insereRegistro() { } // nao ok
      public void removerRegistro() { } //ok
      public void removeRegistro() { } // nao ok
      public String clonarRegistroOutro() { return ""; } // ok
      public String clonaRegistroOutro() { return ""; } // nao ok
      public int inserirRegistroOutro() { return 0; }  // ok
      public int insereRegistroOutro() { return 0; } // nao ok
      public String nome; // ok
    }
  ```

checkstyle.xml
  ```
    <module name="com.codeaesthetics.VerbsInInfinitiveCheck" />
  ```


Métodos booleanos devem iniciar com `has ` ou `is`
  ```
    public class ExampleForPrefixBooleanMethods {
      public boolean isActive() { return true; } //ok 
      public boolean hasSomething() { return true; } // ok
      public boolean check() { return true; } // nao ok
      public boolean verify() { return true; } // nao ok
    }
  ```
checkstyle.xml
  ```
    <module name="com.codeaesthetics.PrefixBooleanMethodsCheck" />
  ```

Não usar notação húngara nas variáveis
  ```
    public class ExampleForHungarianNotation {
      String strNome = ""; //nao ok
      String sNome = ""; //nao ok
      String nome = ""; // ok
      int iContador = 0; //nao ok
      int intContador = 0; //nao ok
      int inicio = 0; //nao ok
      int iAlgumaCoisa = 0; //nao ok
      int intAlgumaCoisa = 0; //nao ok
      float fVar = 0; //nao ok
      double dVar = 0.0; //nao ok
      float var1 = 0; // ok
      double var2 = 0.0; // ok
    }
  ```

checkstyle.xml
  ```
    <module name="com.codeaesthetics.HungarianNotationCheck" />
  ```

Valida a existência de `@Annotations` em métodos públicos em classes com determinados sufixos
  ```
    public class ExampleService {
      @Deprecated
      @SuppressWarnings(value = { "unused" })
      public String algumMetodo(int id) { return ""; } // ok

      @SuppressWarnings(value = { "" }) 
      public String algumMetodoOutro(int id) { return ""; } // nao ok

      private void nadaAqui() { } // ok
    }

    public class ExampleOutro {
      public String algumMetodoOutro(int id) { return ""; } // ok
    }
  ```
checkstyle.xml
  ```
    <module name="com.codeaesthetics.PublicMethodAnnotationsCheck">
      <property name="classSuffixes" value="Serice,Srvc"/>
      <property name="requiredAnnotations" value="Deprecated,SuppressWarnings"/>
    </module>
  ```



## Uso

Copiar o arquivo `code-aesthetics-1.0-SNAPSHOT.jar` de dentro do target e colar na pasta raiz do projeto a ser validado.

Adicionar no `POM.xml` do projeto a ser validado:

No bloco de `<properties></properties>`
  ```
    <!-- CHECKSTYLE -->
    <maven-checkstyle-plugin.version>3.4.0</maven-checkstyle-plugin.version>
  ```

No bloco de `<build>`
  ```
    ...
    <build>
      <pluginManagement>
        <plugins>
          ...
          <!-- incio do uso do checkstyle + code aesthetics -->
          <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-checkstyle-plugin</artifactId>
            <version>3.4.0</version>
            <dependencies>
              <!-- checkstyle -->
              <dependency>
                <groupId>com.puppycrawl.tools</groupId>
                <artifactId>checkstyle</artifactId>
                <version>10.17.0</version>
              </dependency>

              <!-- code esthetics -->
              <dependency>
                <groupId>com.codeaesthetics</groupId>
                <artifactId>code-aesthetics</artifactId>
                <version>1.0</version>
                <scope>system</scope>
                <systemPath>code-aesthetics-1.0-SNAPSHOT.jar</systemPath>
              </dependency>

            </dependencies>

            <configuration>
              <configLocation>src/main/resources/checkstyle.xml</configLocation>
              <consoleOutput>true</consoleOutput>
              <failsOnError>false</failsOnError>
              <violationSeverity>warning</violationSeverity>
              <logViolationCountToConsole>true</logViolationCountToConsole>
            </configuration>

            <executions>
              <execution>
                  <phase>validate</phase>
                  <goals>
                    <goal>check</goal>
                  </goals>
              </execution>
            </executions>
          </plugin>
          <!-- fim do uso do checkstyle + code aesthetics -->

        </plugins>
      </pluginManagement>
    </build>
    ...
  ```


No `checkstyle.xml` do projeto, adicionar os Checks
  ```
    <?xml version="1.0" ?>
    
    <!DOCTYPE module PUBLIC
      "-//Checkstyle//DTD Checkstyle Configuration 1.2//EN"
      "https://checkstyle.org/dtds/configuration_1_2.dtd">
    
    <module name="Checker">
      <module name="TreeWalker">

        <module name="com.codeaesthetics.PrefixBooleanMethodsCheck" />
        <module name="com.codeaesthetics.HungarianNotationCheck" />
        <module name="com.codeaesthetics.VerbsInInfinitiveCheck" />

        <module name="com.codeaesthetics.PublicMethodAnnotationsCheck">
          <property name="classSuffixes" value="Serice,Srvc"/>
          <property name="requiredAnnotations" value="Deprecated,SuppressWarnings"/>
        </module>

      </module>
    </module>
  ```

0. Para instalar localmente, rodar:
  ```
    $ mvn install:install-file -Dfile=code-aesthetics-1.0-SNAPSHOT.jar -DgroupId=com.codeaesthetics -DartifactId=code-aesthetics -Dversion=1.0-SNAPSHOT -Dpackaging=jar
```

1. No projeto a ser validado, rodar o checkstyle:
  ```
    $ mvn checkstyle:check
  ```

---

## Modificação

Clonar o projeto
  ```
    $ git clone https://github.com/jpontesmartins/code-aesthetics.git
  ```

Rodar testes
  ```
    $ mvn test
  ```

Gerar JAR
  ```
    $ mvn install 
  ```