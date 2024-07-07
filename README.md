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




