# ChaTop Backend

ChaTop Backend API is a Spring Boot application that provides a RESTful API for managing chat and rental operations with a MySQL
database.

## Getting Started

### Prerequisites

Before getting started, ensure you have the following installed:

- [Java 22.0.1](https://www.oracle.com/java/technologies/downloads/)
- [MySQL](https://www.mysql.com/fr/downloads/)
- [IDE - Development made with Vs code](https://code.visualstudio.com/download)
- Maven
- Angular CLI and Nodes.js
- Java Development Kit (JDK)

### Clone the Project

Clone these repositories :
> git clone https://github.com/oulibouli/Developpez-le-back-end-en-utilisant-Java-et-Spring.git

> git clone https://github.com/oulibouli/chatop_backend.git

### Install Dependencies

#### Frontend:

> npm install

#### Backend:

> mvn clean install

### Configuring the Application

1. Open the [`application.properties`](src/main/resources/application.properties) file located in the `api/src/main/resources` directory of the project.
2. Update the following properties with yours:

```properties
# Database configuration
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
# JWT
jwt.secret.key=${JWT_KEY}
```

### SQL setup

SQL script to create the schema is available by following this path:
[src/main/resources/sql/script.sql](src/main/resources/sql/script.sql)

Do not use the FrontEnd script sql or any other.

## Run the project

### BackEnd:

> mvn spring-boot:run

Or run the app with your IDE.

### FrontEnd:

> ng serve

The application is available at http://localhost:4200/.

## Swagger UI

The Swagger UI is available at http://localhost:3001/swagger-ui/index.html.

To get access to all the APIs :

1. Use the `POST` /api/auth/register endpoint from `Authentication`.
2. Copy the token value from the `Response body`.
3. Click on the `Authorize` button.
4. Paste the token value into the field.
5. Now you can use the others endpoints.

## Routes implemented

See the file :
[Fichier routes.md](src/main/resources/routes.md)

## Dependencies
### org.springdoc
springdoc-openapi-starter-webmvc-ui
### org.springframework.boot
spring-boot-starter-data-jpa
### org.springframework.boot
spring-boot-starter-security
### io.jsonwebtoken
jjwt-api
### io.jsonwebtoken
jjwt-impl
### io.jsonwebtoken
jjwt-jackson
### org.springframework.boot
spring-boot-devtools
### org.springframework.boot
spring-boot-starter-web
### org.springframework.boot
spring-boot-starter-validation
### com.mysql
mysql-connector-j
### org.projectlombok
lombok
### org.springframework.boot
spring-boot-starter-test
### org.springframework.security
spring-security-config
