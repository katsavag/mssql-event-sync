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

## Database Sync - DB Event approach

### Pros:
1. **Reliability**
    - Uses an outbox pattern which ensures reliable message delivery
    - Messages are persisted before processing, preventing data loss
    - Handles failures gracefully with error logging

2. **Consistency**
    - Transactions are managed () `@Transactional`
    - Events are processed in order (ordered by newest first)
    - Each operation type (INSERT, UPDATE, DELETE) has dedicated handling

3. **Maintainability**
    - Clear separation of concerns
    - Well-structured code with distinct methods for different operations
    - Uses DTOs for data transfer, providing a clean interface

4. **Monitoring**
    - Comprehensive logging for tracking synchronization progress
    - Logs both successful operations and errors
    - Provides visibility into the number of records processed

5. **Automation**
    - Automated synchronization using Spring's annotation `@Scheduled`
    - Regular execution (every 60 seconds) without manual intervention

### Cons:
1. **Performance Issues**
    - Processes messages one by one, which could be slow for large datasets
    - No batch processing capability
    - Fixed 60-second schedule might not be optimal for all scenarios

2. **Error Handling Limitations**
    - No retry mechanism for failed operations
    - Messages remain in an error state without automatic recovery
    - Logging could be more structured (currently using basic Java logging)

3. **Data Consistency Risks**
    - No version control or timestamp checking for updates
    - Potential race conditions between sync cycles
    - No mechanism to handle conflicting updates

4. **Resource Usage**
    - Keeps querying for unpublished messages every minute even if none exist
    - No backoff strategy when errors occur
    - Could create unnecessary database load

5. **Implementation Gaps**
    - Missing cleanup of processed messages
    - No handling of partial failures
    - Warning logs are placed incorrectly in some methods

6. **Scalability Concerns**
    - Single-threaded processing
    - No parallel processing capability
    - Might not handle high volumes efficiently

7. **Monitoring Limitations**
    - No metrics collection
    - No health checks
    - Limited alerting capabilities

## Other Approaches

### 1. Replication
- **Description**: Native database replication features
- **Examples**:
    - MySQL Primary-Secondary replication
    - PostgreSQL streaming replication
    - SQL Server Always On

- **Pros**:
    - Built-in database feature
    - Real-time or near real-time sync
    - Minimal application code needed

- **Cons**:
    - Usually requires same database vendor
    - Can be complex to set up
    - May require enterprise licenses

### 2. ETL Tools
- **Description**: Specialized tools for data extraction, transformation, and loading
- **Examples**:
    - Apache NiFi
    - Talend
    - Informatica

- **Pros**:
    - Visual workflow design
    - Built-in transformations
    - Support for multiple data sources

- **Cons**:
    - Can be expensive
    - Additional tool to maintain
    - May introduce latency

### 3. Message Queue Based
- **Description**: Using message brokers to propagate changes
- **Examples**:
    - Apache Kafka
    - RabbitMQ
    - Amazon SQS

- **Pros**:
    - Decoupled architecture
    - Reliable message delivery
    - Good for high-volume systems

- **Cons**:
    - Additional infrastructure needed
    - More complex architecture
    - Requires message ordering handling

### 4. Change Data Capture (CDC)
- **Description**: Capturing and delivering change events from source database
- **Examples**:
    - Debezium
    - Oracle GoldenGate
    - SQL Server CDC

- **Pros**:
    - Real-time capture
    - Minimal impact on source
    - Captures all changes

- **Cons**:
    - Can be resource-intensive
    - May require specific database versions
    - Complex setup

### 5. API-Based Synchronization
- **Description**: Using REST/GraphQL APIs to sync data
- **Pros**:
    - Platform-independent
    - Easy to implement
    - Good for loose coupling

- **Cons**:
    - Higher latency
    - More network overhead
    - Need to handle API failures

### 6. Batch Synchronization
- **Description**: Periodic full or incremental data dumps
- **Pros**:
    - Simple to implement
    - Works across different databases
    - Low real-time overhead

- **Cons**:
    - Not real-time
    - Resource intensive during sync
    - Complex conflict resolution

### 7. Database Federation
- **Description**: Using database links or federation services
- **Examples**:
    - Oracle Database Links
    - SQL Server Linked Servers

- **Pros**:
    - Native database integration
    - Real-time access
    - Transparent to applications

- **Cons**:
    - Performance overhead
    - Security concerns
    - Vendor lock-in

## Improvements:
1. **Add batch processing** to improve performance
2. **Implement retry mechanism** with exponential backoff
3. **Add version control** for data consistency
4. **Include cleanup mechanism** for processed messages
5. **Implement metrics** for better monitoring
6. **Add parallel processing** capability for better scalability
7. **Include health checks** and proper alerting
8. **Fix incorrect log placement** in handler methods
9. **Add proper error recovery** mechanisms
10. **Consider using a more robust logging framework** like SLF4J

## Developer

- Evangelos Katsadouros (katsadouros.v@gmail.com)