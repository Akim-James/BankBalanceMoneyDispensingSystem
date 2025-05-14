# Bank Balance Dispensing System (BBDS)

## Overview
The Bank Balance Dispensing System (BBDS) is a modern banking application built with Spring Boot that manages ATM cash dispensing operations. The system handles cash withdrawals while maintaining accurate account balances and ATM cash inventories.

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

### Services
- `CashWithdrawalService`: Manages the cash withdrawal process, including account validation, funds verification, and cash dispensing.

### Repositories
- `AtmRepository`: Manages ATM entity operations
- `ClientAccountRepository`: Handles client account data operations

### DTOs and Models
- `DenominationDto`: Represents currency denomination data
- `CashWithdrawalResponse`: Encapsulates withdrawal transaction response

### Entities
- `Atm`: Represents ATM machine data
- `AtmAllocation`: Manages ATM cash denominations
- `ClientAccount`: Stores client account information

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

## API Usage

### Cash Withdrawal

Request body:
```PLAINTEXT
json { "clientId": 1, "accountNumber": 1234567890, "atmId": 1, "amount": 1000.00 }
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
