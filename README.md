# api-rest-people-management
Desenvolvimento de um pequeno sistema para o gerenciamento de pessoas de uma empresa através de uma API REST, criada com o Spring Boot.

Tecnologias utilizadas

* Java11
* Maven 3.6.3
* [Site oficial do Spring](https://spring.io/)
* [Site oficial do Heroku](https://www.heroku.com/)
* [Documentação oficial do Lombok](https://projectlombok.org/)
* [Documentação oficial do Map Struct](https://mapstruct.org/)
* [SDKMan! para gerenciamento e instalação do Java e Maven](https://sdkman.io/)
* Mock

###Execução

Para executar o projeto no terminal, digite o seguinte comando:

```shell script
mvn spring-boot:run 
```

Após executar o comando acima, basta apenas abrir o seguinte endereço e visualizar a execução do projeto:

```
http://localhost:8080/api/v1/people
```

####Comandos SDKMan!

Listar sdks
```
sdk list java | less
```
Instalar sdk passando versão
```
sdk install java 11.0.11.hs-adpt
```

Definir qual sdk estara em uso.
```
sdk use java 11.0.11.hs-adpt
```

Maven
```
sdk list maven
```

```
sdk install maven 3.8.1
```

```
sdk use maven 3.8.1
```


