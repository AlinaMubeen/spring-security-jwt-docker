# Spring Security JWT Authentication

A Spring Boot application implementing **JWT-based authentication and role-based authorization** using Spring Security. The project also includes database integration with PostgreSQL, Docker containerization, and unit/web-layer testing with JUnit 5 and Mockito.

## 🚀 Features

* User registration
* Secure password hashing using BCrypt
* JWT-based authentication
* JWT token validation
* Role-based authorization
* `USER` and `ADMIN` roles
* Protected REST endpoints
* PostgreSQL database integration
* JPA/Hibernate for database operations
* Docker and Docker Compose support
* JUnit 5 testing
* Mockito-based mocking
* Input validation
* Stateless authentication using JWT

## 🛠️ Technologies

| Technology      | Purpose                         |
| --------------- | ------------------------------- |
| Java            | Programming language            |
| Spring Boot     | Backend framework               |
| Spring Security | Authentication & authorization  |
| JWT             | Token-based authentication      |
| JPA / Hibernate | ORM and database access         |
| PostgreSQL      | Relational database             |
| Maven           | Dependency management and build |
| Docker          | Containerization                |
| JUnit 5         | Testing                         |
| Mockito         | Mocking and unit testing        |

## 🔐 Authentication Flow

The application uses JWT for stateless authentication.

```text
Client
  │
  │ Login (username + password)
  ▼
Spring Security
  │
  │ Authenticate user
  ▼
Database
  │
  │ User verified
  ▼
JWT Service
  │
  │ Generate JWT
  ▼
Client
  │
  │ Authorization: Bearer <JWT>
  ▼
JWT Authentication Filter
  │
  │ Validate token
  ▼
Spring Security
  │
  │ Check user role
  ▼
Protected Endpoint
```

## 👥 Authorization

The application supports two roles:

* `USER`
* `ADMIN`

Example protected endpoints:

```text
GET /user/home
GET /admin/home
```

A user with `ROLE_USER` can access user endpoints, while a user with `ROLE_ADMIN` can access admin endpoints.

## 📌 API Endpoints

### Register

```http
POST /register
```

Example request:

```json
{
  "username": "abc",
  "password": "password123",
  "role": "USER"
}
```

### Login

```http
POST /login
```

Example request:

```json
{
  "username": "abc",
  "password": "password123"
}
```

The response contains the generated JWT:

```text
eyJhbGciOiJIUzI1NiJ9...
```

### User Endpoint

```http
GET /user/home
```

Header:

```text
Authorization: Bearer <JWT>
```

### Admin Endpoint

```http
GET /admin/home
```

Header:

```text
Authorization: Bearer <JWT>
```

## 🐳 Running with Docker

The project includes a `Dockerfile` and `docker-compose.yml`.

Build and start the application:

```bash
docker compose up --build
```

Check running containers:

```bash
docker ps
```

Stop the containers:

```bash
docker compose down
```

PostgreSQL runs as a separate container and communicates with the Spring Boot application through the Docker network.

## ⚙️ Environment Variables

Sensitive configuration such as database passwords and JWT secrets should be stored in a `.env` file.

Example:

```env
POSTGRES_PASSWORD=your_password
JWT_SECRET=your_secret_key
```

The `.env` file is excluded from Git using `.gitignore`.

For security, **never commit real passwords, database credentials, or JWT secrets to GitHub.**

## 🧪 Testing

The project uses:

* **JUnit 5** for testing
* **Mockito** for mocking dependencies
* **Spring Boot Test / MockMvc** for testing web endpoints

Run the tests with:

```bash
mvn test
```

Or using the Maven wrapper:

```bash
./mvnw test
```

On Windows:

```bash
mvnw.cmd test
```

## 📁 Project Structure

```text
src/
├── main/
│   ├── java/
│   │   └── com/example/springSecurity/
│   │       ├── Controller/
│   │       ├── Model/
│   │       ├── Repository/
│   │       ├── Service/
│   │       ├── SecurityConfig.java
│   │       └── jwtAuthenticationFilter.java
│   │
│   └── resources/
│       └── application.properties
│
└── test/
    └── java/
        └── ...
        
Dockerfile
docker-compose.yml
pom.xml
.gitignore
```

## 🔒 Security

Passwords are never stored as plain text. They are encoded using BCrypt before being saved to the database.

JWT tokens are validated before protected resources are accessed, and role-based authorization prevents users from accessing endpoints they are not authorized to use.

## 🎯 Learning Objectives

This project was developed to practice:

* Spring Boot backend development
* Spring Security
* JWT authentication
* Role-based authorization
* JPA/Hibernate
* PostgreSQL
* REST APIs
* Docker containerization
* Unit and integration testing
* JUnit 5 and Mockito

## 👩‍💻 Author

**Alina Mubeen**

This project was developed as a learning and portfolio project to demonstrate backend development and Spring Security concepts.
