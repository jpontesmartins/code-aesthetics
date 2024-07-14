code-aesthetics
---
Dependência para usar junto com o `checkstyle` plugin para validar as nomenclaturas do código

| Regra                                             | Classe de Check           | Exemplo                                  |
| ------------------------------------------------- | ------------------------- | ---------------------------------------- |
| Métodos booleanos devem iniciar com `has` ou `is` | PrefixBooleanMethodsCheck | `isActive` ou `hasItems`                 |
| Não usar notação húngara nas variáveis            | HungarianNotationCheck    | `nome` em vez de `strNome`               |
| Verbos devem estar no infinitivo                  | VerbsInInfinitiveCheck    | `inserirPessoa` em vez de `inserePessoa` |
| Valida existência de `@Annotations` em métodos públicos | PublicMethodAnnotationsCheck | | 


## Alteração

1. Clonar o projeto
  ```
    $ git clone https://github.com/jpontesmartins/code-aesthetics.git
  ```

2. Rodar testes
  ```
    $ mvn test
  ```

3. Gerar JAR
  ```
    $ mvn install 
  ```

## Uso do My Aesthetics

1. Copiar o arquivo `code-aesthetics-1.0-SNAPSHOT.jar` de dentro do target e colar na pasta raiz do projeto a ser validado.

2. Adicionar no `POM.xml` do projeto a ser validado:


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
          <!-- Incio do uso do Checkstyle + My Aesthetics -->
          <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-checkstyle-plugin</artifactId>
            <version>3.4.0</version>
            <dependencies>
              <!-- Checkstyle -->
              <dependency>
                <groupId>com.puppycrawl.tools</groupId>
                <artifactId>checkstyle</artifactId>
                <version>10.17.0</version>
              </dependency>

              <!-- My Aesthetics -->
              <dependency>
                <groupId>com.codeaesthetics</groupId>
                <artifactId>code-aesthetics</artifactId>
                <version>1.0</version>
                <scope>system</scope>
                <systemPath>my-aesthetics-1.0-SNAPSHOT.jar</systemPath>
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
          <!-- Fim do uso do Checkstyle + My Aesthetics -->

        </plugins>
      </pluginManagement>
    </build>
    ...
  ```


3. No `checkstyle.xml` do projeto, adicionar os Checks
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

      </module>
    </module>
  ```

4. No projeto a ser validado, rodar o checkstyle:
  ```
    $ mvn checkstyle:check
  ```



