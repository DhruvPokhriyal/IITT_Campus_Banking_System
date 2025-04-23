# IIT Banking System

[![Java Version](https://img.shields.io/badge/Java-21-orange)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.3-brightgreen)](https://spring.io/projects/spring-boot)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

A robust and secure banking management system designed specifically for the IIT campus community. This system provides a comprehensive solution for managing financial transactions, accounts, and banking operations within the campus environment.

## 🚀 Features

-   **Secure Authentication & Authorization**

    -   JWT-based authentication
    -   Role-based access control
    -   Secure password handling

-   **Account Management**

    -   Account creation and maintenance
    -   Balance tracking
    -   Transaction history

-   **Transaction Services**

    -   Fund transfers
    -   Payment processing
    -   Transaction logging and monitoring

-   **Security Features**
    -   Encrypted data transmission
    -   Secure session management
    -   Input validation and sanitization

## 🛠️ Tech Stack

-   **Backend Framework:** Spring Boot 3.2.3
-   **Programming Language:** Java 21
-   **Database:** PostgreSQL
-   **Security:** Spring Security, JWT
-   **API Documentation:** OpenAPI (Springdoc)
-   **Build Tool:** Maven
-   **Additional Libraries:**
    -   Lombok for boilerplate reduction
    -   Hibernate Validator for data validation
    -   Spring Data JPA for database operations

## 📋 Prerequisites

-   Java Development Kit (JDK) 21
-   Maven 3.6+
-   PostgreSQL 12+
-   Your favorite IDE (IntelliJ IDEA recommended)

## 🔧 Setup & Installation

1. **Clone the repository**

    ```bash
    git clone https://github.com/yourusername/iit-banking-system.git
    cd iit-banking-system
    ```

2. **Configure PostgreSQL**

    - Create a new database
    - Update database credentials in `application.properties`

3. **Build the project**

    ```bash
    mvn clean install
    ```

4. **Run the application**
    ```bash
    mvn spring-boot:run
    ```

The application will be available at `http://localhost:8080`

## 📁 Project Structure

```
src/main/java/com/iit/banking/
├── config/          # Configuration classes
├── controller/      # REST API controllers
├── dto/            # Data Transfer Objects
├── exceptions/     # Custom exception handlers
├── model/          # Entity classes
├── repository/     # Data access layer
├── service/        # Business logic layer
└── enums/          # Enumeration classes
```

## 🔍 API Documentation

The API documentation is available through Swagger UI when the application is running:

-   Swagger UI: `http://localhost:8080/swagger-ui.html`
-   OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## 🤝 Contributing

We welcome contributions to the IIT Banking System! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📜 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

-   IIT Campus Administration for their support
-   All contributors who have helped shape this project

## 📞 Contact

For any queries or support, please contact:

-   Project Maintainer: [Your Name](mailto:your.email@example.com)
-   IIT Banking Support: [support@iitbanking.com](mailto:support@iitbanking.com)

---

⭐️ Star this repository if you find it helpful!
