# Warehouse

This is a Java REST API that runs a warehouse software.

This software holds articles, and the articles contain an identification number, a name and available stock. It is possible to load articles into the software from a file, see inventory.json.

The warehouse software also have products, products are made of different articles. Products have a name, price, and a list of articles of which they are made from with a quantity. The products also loaded from a file initially, see products.json.

## Getting Started

### Prerequisites

* [Java 11](https://adoptopenjdk.net) - Programming language

### Running the tests

```shell script
./mvnw test
```

### Running the application

```shell script
./mvnw spring-boot:run
```

### Run dockerized
```shell script
./mvnw spring-boot:build-image
docker run -p 8080:8080 docker.io/library/warehouse:0.0.1-SNAPSHOT
```

API endpoint documentation: <http://localhost:8080/api/swagger-ui/>

#### Access Credentials
User role can only inquire entities.
* User: *`user`*
* Password: *`userPassword`*

Admin has all the rights including saving new products, articles and selling products.
* User: *`admin`*
* Password: *`adminPassword`*

## Built With

* [Spring Boot](https://projects.spring.io/spring-boot) - The framework used
* [Maven](https://maven.apache.org) - Dependency management
* [JUnit](https://junit.org) - Test framework
* [Spring Security](https://spring.io/projects/spring-security) - Authentication and access-control framework
* [Swagger](https://swagger.io) - Used to generate API docs & UI

## Author

[Baris Aydinoglu](http://baris.aydinoglu.net)
