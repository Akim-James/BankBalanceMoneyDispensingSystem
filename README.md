# Bank Balance And Cash Dispensing System

Welcome to the `Bank Balance And Cash Dispensing System` project! This application serves as a comprehensive solution for managing client accounts, facilitating cash withdrawals, and querying transactional and currency account balances.

## Project Structure

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

## Setup Instructions

To get started with the Bank Balance Dispensing System, follow these instructions:

1. **Clone the Repository**:
   ```bash
   https://github.com/Akim-James/BankBalanceMoneyDispensingSystem.git