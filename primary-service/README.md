# MSSQL Event Sync - Primary Service

This is a Spring Boot application that provides a REST API for managing users. The service is part of a demonstration project for synchronizing data between two MS SQL Server databases.

## Features

- Create, retrieve, and list users
- RESTful API with JSON responses
- Spring Data JPA for database operations
- MS SQL Server database integration

## API Endpoints

The service exposes the following endpoints:

- `POST /api/users` - Create a new user
- `GET /api/users/{id}` - Get a user by ID
- `GET /api/users` - Get all users (sorted by newest first)

## Swagger Documentation

The API is documented using Swagger/OpenAPI. You can access the Swagger UI at:

```
http://localhost:8080/primary-service/api/swagger-ui.html
```

The OpenAPI specification is available at:

```
http://localhost:8080/primary-service/api/api-docs
```

## Getting Started

### Prerequisites

- Java 21
- Maven
- MS SQL Server (can be run using Docker)

### Running the Application

1. Clone the repository
2. Configure the database connection in `application.properties` if needed
3. Run the application using Maven:

```bash
./mvnw spring-boot:run
```

4. Access the API at `http://localhost:8080/primary-service/api`
5. Access the Swagger UI at `http://localhost:8080/primary-service/api/swagger-ui.html`

## Configuration

The application can be configured through the `application.properties` file:

- Server port: 8080
- Context path: /primary-service/api
- Database connection details
- Hibernate properties

## Developer

- Evangelos Katsadouros (katsadouros.v@gmail.com)