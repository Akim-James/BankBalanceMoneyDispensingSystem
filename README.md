# Bank Balance Dispensing System
## Overview
The Bank Balance Dispensing System is a Spring Boot application that exposes REST endpoints to query client account balances, including currency and transactional balances. The Swagger UI allows developers and testers to interact with these endpoints directly from a web browser.
## Project Details
- **Application Name**: BankBalanceDispensingSystem
- **Context Path**: `/discovery-atm`
- **Swagger UI URL**: `http://localhost:8080/discovery-atm/swagger-ui/index.html#/`

## Features
- Cash withdrawal processing
- Real-time account balance management
- ATM cash inventory tracking
- Denomination-based cash dispensing
- Transaction logging and monitoring
- Secure client account validation

## Technology Stack
- Java 17
- Spring Boot
- Spring Data JPA
- Jakarta EE
- Lombok
- Maven/Gradle (build tool)
- PostgreSQL/MySQL (database)

## Project Structure

## Key Components

Here's a brief overview of the project's package organization:

- **config**: Contains configuration settings for the application.
- **dto**: Data Transfer Objects used within the application.
- **exception**: Custom exceptions leveraged by the services.
- **mapper**: Mapper classes for converting entities to DTOs.
- **repository**: Contains repository interfaces for database entity interactions.
- **service.transactional**: Services handling transactional operations like account querying and cash withdrawal.
- **web**: REST controllers managing HTTP requests from the client.

## Key Features

- **Cash Withdrawal**: Facilitates cash withdrawal transactions, ensuring atomic operations via transaction management.
- **Account Queries**: Fetches transactional and currency account balances for clients.
- **Error Handling**: Implements robust exception handling for invalid operations or queries.
- **Logging**: Utilizes SLF4J for effective logging throughout services.

## Getting Started

### Prerequisites
- JDK 17 or higher
- Maven/Gradle
- Your preferred IDE (IntelliJ IDEA recommended)
- Database (PostgreSQL/MySQL)

### Installation
1. Clone the repository:
```shell
git clone git clone [https://github.com/yourusername/BankBalanceDispensingSystem.git](https://github.com/yourusername/BankBalanceDispensingSystem.git)
```
2. Navigate to the project directory:
```shell
bash cd BankBalanceDispensingSystem
```
3. Build the project:
```shell
 ./mvnw clean install
```
4. Run the application:
```shell
 ./mvnw spring-boot:run
```

## Configuration
The application can be configured through `application.properties`/`application.yml` files:
- Database connection settings
- Logging levels
- Server configurations

## Security
- Transactional integrity is maintained using Spring's `@Transactional`
- Input validation and error handling
- Secure account verification

## Logging
The application uses SLF4J for logging, with different log levels:
- INFO: Transaction processing
- DEBUG: Detailed operation tracking
- ERROR: Exception and error handling

## API Endpoints
The application currently exposes the following endpoints for querying client account balances:
- - Retrieve currency account balances for a specific client `GET /queryCcyBalances/{clientId}`
- - Retrieve transactional account balances for a specific client `GET /queryTransactionalBalances/{clientId}`

## Prerequisites
Before you can access the Swagger UI, ensure that:
- Java 17 is installed on your machine
- Maven is installed for building and running the project
- The project is cloned or downloaded to your local machine
- The Spring Boot application is running locally on port 8080

## Setup and Running the Application
### 1. Clone the Repository
``` bash
git clone <repository-url>
cd bank-balance-dispensing-system
```
### 2. Build the Project
``` bash
mvn clean install
```
### 3. Run the Application
``` bash
mvn spring-boot:run
```
Alternatively, you can run the main class directly from your IDE.
### 4. Verify Application Startup
Ensure the application starts successfully. You should see log messages indicating that the server is running on . `http://localhost:8080`
## Accessing Swagger UI
1. Open a web browser
2. Navigate to:
    - The context path is configured in the application properties `/discovery-atm`
    - The Swagger UI path is configured via `/swagger-ui.html``springdoc.swagger-ui.path`

`http://localhost:8080/discovery-atm/swagger-ui/index.html#/`

## Testing API Endpoints Using Swagger UI
### 1. Explore the API Documentation
- In the Swagger UI, you will see two endpoints listed:
    - `GET /queryCcyBalances/{clientId}`
    - `GET /queryTransactionalBalances/{clientId}`

- Click on any endpoint to expand it and view details, including parameters, response models, and example requests

### 2. Test an Endpoint
1. Click on the endpoint you want to test
2. Click the "Try it out" button
3. Enter a value for the `clientId` parameter (e.g., 1)
4. Click the "Execute" button
5. Review the response (status code, headers, and body)

### 3. Review the Response
- Check if the response body matches the expected format
- For errors, note the appropriate status code and message

## Configuration Details
- **Application Context Path**: () `/discovery-atm``server.servlet.context-path`
- **Swagger UI Path**: () `/swagger-ui.html``springdoc.swagger-ui.path`
- **API Documentation Path**: () `/v3/api-docs``springdoc.api-docs.path`
- **Database**: In-memory H2 database (accessible at if enabled) `http://localhost:8080/discovery-atm/h2-console`

## Troubleshooting
### Common Issues
1. **Application Not Running**: Verify the application is running on `http://localhost:8080`
2. **Incorrect URL**: Verify the complete URL including context path
3. **Port Conflict**: Configure a different port in if 8080 is in use `application.properties`
4. **Swagger UI Not Displaying**: Check the dependency `springdoc-openapi-starter-webmvc-ui`
5. **API Errors**: Verify database initialization and test data availability

## Additional Notes
- Swagger UI is provided by the library `springdoc-openapi-starter-webmvc-ui`
- Customize documentation using or annotations like and `application.properties``@Operation``@ApiResponse`
- H2 database data resets on application restart unless configured for persistence
