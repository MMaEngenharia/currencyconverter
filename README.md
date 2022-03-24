Currency Converter
===
## Propósito
Este projeto tem a finalidade de calcular o câmbio entre moedas em tempo real, utilizando a taxa de conversão da cotação do dia. 

## Recursos
| Verbo | EndPoints                                           | Descrição|
| ------| :---------------------------------------------------|:----------------------|
| POST  | http://localhost:9999/api/v1/user                   | adiciona  usuário |
| POST  | http://localhost:9999/api/v1/transaction/convert    | efetua transação|
| GET   | http://localhost:9999/api/v1/transaction/all/user/1 | Retorna as transações realizadas|

##### Adicionar usuário
```
POST http://localhost:9999/api/v1/user
BODY:
{
  "username":"admin",
  "password":"admin"
}
``` 
##### Efetuar a transação
```
POST http://localhost:9999/api/v1/transaction/convert
BODY:
{
  "from":"BRL",
  "to":"USD",
  "amount": 2.0,
  "user": {
    "id": 1
  }
}
``` 

##### Retornar as transações realizadas por usuário
```
GET http://localhost:9999/api/v1/transaction/all/user/1
``` 

## Tecnologias
- Java 8
- Spring Boot
- H2
- junit

## Organização das camadas.

 ```
src
 |- main
      |- com
          |- currencyconverter
                |- confing
                     |- security
                |- controller
                |- model
                |- repository
                |- service
                |- util
                     |- api
                         |- data
                     |- exception
 |- test
      |- java
           |- com
               |- currencyconverter                    
```

## Como usar
Para utilizar o serviço será necessário um cliente rest

- Postman https://www.postman.com/product/rest-client/

### Para executar os testes utilize o comando abaixo.

Windows 
```shell script
./mvnw.cmd package
```
ou linux / MacOs 
```shell script
./mvnw package
```

### Iniciar o serviço
```shell script
java -jar currencyconverter-1.0.0.jar
```

