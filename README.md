<h1 align="center">
  Simple Pay
</h1>

O Simple Pay é um sistema de pagamentos simplificado com regras específicas de negócio.

As informações sobre o desafio estão no [repositório](https://github.com/PicPay/picpay-desafio-backend) do PicPay.

## Tecnologias
 
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [H2](https://www.h2database.com)

## Como Executar

- Clonar repositório git:
```
git clone https://github.com/davidmateusreis/simple-pay.git
```
- Construir o projeto:
```
./mvnw clean package
```
- Executar:
```
java -jar ./target/simple-pay-0.0.1-SNAPSHOT.jar
```
A ferramenta utilizada nos testes foi o [httpie](https://httpie.io):

- Criar usuários
```
http POST :8080/users firstName=David lastName=Mateus document=123456781 email=david@email.com password=123456 balance=100 userType=COMMON

HTTP/1.1 201
Connection: keep-alive
Content-Type: application/json
Date: Tue, 05 Sep 2023 17:35:59 GMT
Keep-Alive: timeout=60
Transfer-Encoding: chunked

{
    "balance": 100,
    "document": "123456781",
    "email": "david@email.com",
    "firstName": "David",
    "id": 1,
    "lastName": "Mateus",
    "password": "123456",
    "userType": "COMMON"
}

http POST :8080/users firstName=David lastName=Mateus document=123456782 email=mateus@email.com password=123456 balance=100 userType=MERCHANT

HTTP/1.1 201
Connection: keep-alive
Content-Type: application/json
Date: Tue, 05 Sep 2023 17:37:47 GMT
Keep-Alive: timeout=60
Transfer-Encoding: chunked

{
    "balance": 100,
    "document": "123456782",
    "email": "mateus@email.com",
    "firstName": "David",
    "id": 2,
    "lastName": "Mateus",
    "password": "123456",
    "userType": "MERCHANT"
}

```
- Realizar transação
```
http POST :8080/transactions sender=1 receiver=2 value=100

HTTP/1.1 200
Connection: keep-alive
Content-Type: application/json
Date: Tue, 05 Sep 2023 17:38:26 GMT
Keep-Alive: timeout=60
Transfer-Encoding: chunked

{
    "amount": 100,
    "createdAt": "2023-09-05T14:38:26.826314",
    "id": 1,
    "receiver": {
        "balance": 200.0,
        "document": "123456782",
        "email": "mateus@email.com",
        "firstName": "David",
        "id": 2,
        "lastName": "Mateus",
        "password": "123456",
        "userType": "MERCHANT"
    },
    "sender": {
        "balance": 0.0,
        "document": "123456781",
        "email": "david@email.com",
        "firstName": "David",
        "id": 1,
        "lastName": "Mateus",
        "password": "123456",
        "userType": "COMMON"
    }
}
```