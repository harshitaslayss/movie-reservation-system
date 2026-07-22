# Movie Reservation System

A backend Movie Reservation System built using Spring Boot that enables secure user authentication, movie and theatre management, show scheduling, and seat reservation. The project focuses on building a scalable REST API using modern backend development practices.

## Features

### Authentication
- User registration and login
- JWT-based authentication
- Role-based authorization for Admin and User
- Password encryption using BCrypt

### Movie Management
- Create, update, delete, and view movies
- Pagination and sorting
- Dynamic filtering using Spring Data JPA Specifications

### Theatre and Hall Management
- Theatre and hall CRUD operations
- Automatic seat generation for newly created halls

### Show Management
- Create and manage shows
- Automatic ShowSeat generation
- Validation to prevent overlapping shows in the same hall
- Filter shows by movie, theatre, and date

### Reservation Management
- Browse available shows and seats
- Book multiple seats in a single reservation
- Cancel reservations
- View reservation history
- Seat availability validation
- Transactional booking workflow

### Additional Features
- Global exception handling
- Response DTOs
- Redis caching
- Dockerized PostgreSQL database
- Swagger API documentation

## Tech Stack

- Java
- Spring Boot
- Spring Security
- JWT
- Spring Data JPA
- PostgreSQL
- Redis
- Docker & Docker Compose
- Maven
- Swagger (OpenAPI)
- JUnit 5
- Mockito

## Project Structure

```
src
├── config
├── controller
├── dto
├── entity
├── exception
├── repository
├── security
├── service
└── util
```

## Getting Started

### Clone the repository

```bash
git clone https://github.com/harshitaslayss/movie-reservation-system.git
cd movie-reservation-system
```

### Start PostgreSQL and Redis

```bash
docker compose up -d
```

### Configure Environment Variables

```
DB_USERNAME=your_username
DB_PASSWORD=your_password
JWT_SECRET=your_secret_key
```

### Run the application

```bash
./mvnw spring-boot:run
```

Or run the application directly from your IDE.

## API Documentation

Once the application is running, Swagger UI is available at:

```
http://localhost:8080/swagger-ui/index.html
```

Swagger can be used to explore and test all available REST endpoints, including authenticated APIs.

## Future Improvements

- Payment gateway integration
- Email notifications
- Recommendation engine
- Microservices architecture
- Kafka-based event processing

## Author

Harshita Sharma

GitHub: https://github.com/harshitaslayss
