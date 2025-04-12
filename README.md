# IIT Banking System

A full-stack banking application built with Spring Boot and React, providing a secure and efficient platform for managing bank accounts, transactions, and user administration.

## Features

### User Features

-   User registration and authentication
-   Account management
-   View account balance
-   Deposit and withdrawal operations
-   Fund transfers between accounts
-   Transaction history

### Admin Features

-   Admin registration and authentication
-   User management (view, delete users)
-   Transaction management
-   Transaction reversal capability
-   System monitoring

## Tech Stack

### Backend

-   Java 17
-   Spring Boot 3.x
-   Spring Security with JWT
-   PostgreSQL
-   Hibernate/JPA
-   OpenAPI/Swagger documentation
-   Maven

### Frontend

-   React
-   TypeScript
-   Material-UI
-   Axios for API calls
-   React Router for navigation
-   Context API for state management

## Prerequisites

-   Java 17 or higher
-   Node.js 16 or higher
-   PostgreSQL 14 or higher
-   Maven 3.8 or higher

## Installation

### Backend Setup

1. Clone the repository:

```bash
git clone https://github.com/yourusername/iit-banking-system.git
cd iit-banking-system
```

2. Configure the database:

-   Create a PostgreSQL database named `iit_banking`
-   Update the database credentials in `src/main/resources/application.properties`

3. Build and run the backend:

```bash
mvn clean install
mvn spring-boot:run
```

The backend will start on `http://localhost:8080`

### Frontend Setup

1. Navigate to the frontend directory:

```bash
cd frontend
```

2. Install dependencies:

```bash
npm install
```

3. Start the development server:

```bash
npm start
```

The frontend will start on `http://localhost:3000`

## API Documentation

The API documentation is available at `http://localhost:8080/swagger-ui.html` when the backend is running.

For detailed API documentation, refer to `api-documentation.json` in the project root.

## Project Structure

```
iit-banking-system/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/iit/banking/
│   │   │       ├── config/         # Configuration classes
│   │   │       ├── controller/     # REST controllers
│   │   │       ├── dto/           # Data Transfer Objects
│   │   │       ├── model/         # Entity models
│   │   │       ├── repository/    # JPA repositories
│   │   │       ├── service/       # Business logic
│   │   │       └── util/          # Utility classes
│   │   └── resources/
│   │       ├── application.properties
│   │       └── schema.sql
│   └── test/                      # Test classes
├── frontend/                      # React frontend
├── api-documentation.json         # API documentation
└── pom.xml                        # Maven configuration
```

## Security Features

-   JWT-based authentication
-   Password encryption
-   Role-based access control
-   CORS configuration
-   Input validation
-   SQL injection prevention

## Database Schema

The application uses the following main tables:

-   `admins`: Stores admin user information
-   `users`: Stores regular user information
-   `accounts`: Stores bank account details
-   `transactions`: Records all financial transactions

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgments

-   Spring Boot team for the amazing framework
-   React team for the frontend library
-   All contributors and maintainers
