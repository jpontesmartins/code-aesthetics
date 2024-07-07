my-aesthetics


Comando para criação do projeto
```
$ mvn archetype:generate 
  -DgroupId=com.myaesthetics
  -DartifactId=my-aesthetics 
  -DarchetypeArtifactId=maven-archetype-quickstart 
  -DarchetypeVersion=1.4 
  -DinteractiveMode=false
```

Empacotar projeto (JAR)
```
$ mvn package
```

Executar projeto

```
$ java -cp target/my-aesthetics-1.0-SNAPSHOT.jar com.myaesthetics.App
```

Dependência do Checkstyle
```
<!-- https://mvnrepository.com/artifact/com.puppycrawl.tools/checkstyle -->
<dependency>
    <groupId>com.puppycrawl.tools</groupId>
    <artifactId>checkstyle</artifactId>
    <version>10.17.0</version>
</dependency>
```

## Usar o My Aesthetics

Rodar testes
```
$ mvn test
```

Gerar JAR
```
$ mvn install 
```

Copiar o arquivo `my-aesthetics-1.0-SNAPSHOT.jar` de dentro do target e colar na pasta raiz do projeto a ser validado.

Adicionar no `POM.xml` do projeto a ser validado
```
...
<build>
  <pluginManagement>
    <plugins>
      ...

      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-checkstyle-plugin</artifactId>
        <version>3.4.0</version>
        <dependencies>
          <!-- Checkstyle -->
          <dependency>
            <groupId>com.puppycrawl.tools</groupId>
            <artifactId>checkstyle</artifactId>
            <version>9.3</version>
          </dependency>

          <!-- My Aesthetics -->
          <dependency>
            <groupId>com.myaesthetics</groupId>
            <artifactId>my-aesthetics</artifactId>
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
    </plugins>
  </pluginManagement>
</build>
...
```



