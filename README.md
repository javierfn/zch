# Zara challenge

Project concept of a java backend to create an API using:

- API First (https://cloudappi.net/metodologia-api-first/
- TDD (https://www.paradigmadigital.com/dev/tdd-como-metodologia-de-diseno-de-software/)
- Hexagonal architecture (https://medium.com/@edusalguero/arquitectura-hexagonal-59834bb44b7f)
- OpenAPI 3 (API First) (https://swagger.io/specification/)
- SpringBoot 3 (https://spring.io/blog/2022/05/24/preparing-for-spring-boot-3-0)
- Spring DATA JPA (https://spring.io/projects/spring-data-jpa)
- MapStruct (https://mapstruct.org/)
- Liquibase (https://www.liquibase.org/)
- H2 Database (http://h2database.com/)
- Unitary tests with JUnit and Mockito (https://site.mockito.org/)
- Maven (https://maven.apache.org/)
- Multilanguage
- Docker with Spring

## Requirements

- [Java 17 o superior](https://openjdk.org/projects/jdk/17/)
- [Maven](https://maven.apache.org/)
- [Spring Boot 3](https://spring.io/projects/spring-boot)
- [OpenAPI](https://swagger.io/docs/specification/about/) (OpenAPI First)
- [Docker](https://www.docker.com/)

## Project configuration

Make sure you have Java 17 installed on your system. You can verify it by running:


```bash
java -version
```

Make sure you have Maven installed on your system using Java 17. You can verify this by running:

```bash
mvn -version
```

Make sure you have Docker installed on your system. You can verify this by running:

```bash
docker -v
```

## Project compilation

You can compile and create docker image by running the following command in the root directory of the project

```bash
mvn clean package install
```

## Run the project in local environment with maven

You can run it using the following command inside the app directory of the project:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=custom
```

## Generate docker image and push to repository

You can generate it using the following command inside the app directory of the project:

```bash
docker build -t zch --build-arg JAR_FILE=zch.jar . 
```

## Run the project with the docker image

You can generate it using the following command:

```bash
docker run -p 8080:8080 zch:latest
```

## Generate dependency check report

You can run it using the following command:

```bash
mvn dependency-check:check
```

## Tests

Request examples with response HTTP 200 OK

```bash
curl http://localhost:8080/product/1/similar
```
Response:
```json
[
  {
    "id": "2",
    "name": "Dress",
    "price": 19.99,
    "availability": false
  },
  {
    "id": "3",
    "name": "Blazer",
    "price": 29.99,
    "availability": true
  },
  {
    "id": "4",
    "name": "Boots",
    "price": 39.99,
    "availability": true
  }
]
```

```bash
curl http://localhost:8080/product/9/similar
```
Response:
```json
[
  {
    "id": "11",
    "name": "Cotton T-shirt",
    "price": 39.99,
    "availability": false
  },
  {
    "id": "15",
    "name": "Button-up shirt",
    "price": 49.99,
    "availability": true
  },
  {
    "id": "19",
    "name": "Linen pants",
    "price": 29.99,
    "availability": true
  }
]
```

Request example with error response HTTP 400 BAD REQUEST

```bash
curl http://localhost:8080/product/1001a/similar
```
Response:
```json
{
  "code": 400,
  "message": "For input string: \"1001a\"",
  "timestamp": 1707755154.386465,
  "date": "2024-02-12T16:25:54.386465Z",
  "path": "uri=/product/1001a/similar"
}
```

Request example with error response HTTP 404 NOT FOUND

```bash
curl http://localhost:8080/product/1001/similar
```

Response:
```json
{
  "code": 404,
  "message": "Product not found",
  "timestamp": 1707755124.6449366,
  "date": "2024-02-12T16:25:24.6449366Z",
  "path": "uri=/product/1001/similar"
}
```

## Swagger UI

The swagger interface relative URL path is:

```bash
/swagger-ui/index.html
```

Request example

```bash
curl http://localhost:8080/swagger-ui/index.html
```

## H2 Console

The H2 console relative path is:

```bash
/h2-console
```

URL request example

```bash
http://localhost:8080/h2-console

Driver Class: org.h2.Driver
JDBC URL: jdbc:h2:mem:testdb
User Name: sa
Password:  sa
```

## Docker images of the project

https://hub.docker.com/repository/docker/javierfn/zch/general


## Author

- Name: Javier
- email: javierfn@gmail.com
- GitHub profile: https://github.com/javierfn
- Dockerhub profile: https://hub.docker.com/repositories/javierfn
