# Product Catalog API

A Spring Boot REST API for managing product catalog records with PostgreSQL persistence. This project is structured as a portfolio-ready backend service with validation, DTOs, exception handling, tests, and Docker support.

## Tech Stack

- Java 17
- Spring Boot 3
- Spring Web
- Spring Data JPA
- PostgreSQL
- H2 for tests
- JUnit 5, Mockito, MockMvc
- Docker and Docker Compose
- OpenAPI/Swagger UI

## Features

- Create, read, update, and delete products
- Search products by name
- DTO-based request and response models
- Request validation with clear `400 Bad Request` responses
- Centralized `404 Not Found` handling
- PostgreSQL-backed persistence
- Docker Compose setup for local development
- Unit and controller tests

## API Endpoints

| Method | Endpoint | Description |
| --- | --- | --- |
| `GET` | `/api/products` | List all products |
| `GET` | `/api/products/{id}` | Get a product by id |
| `GET` | `/api/products/search/{name}` | Search a product by name |
| `POST` | `/api/products` | Create a product |
| `PUT` | `/api/products/{id}` | Update a product |
| `DELETE` | `/api/products/{id}` | Delete a product |

## Example Request

```http
POST /api/products
Content-Type: application/json

{
  "name": "MacBook Pro",
  "type": "Laptop",
  "place": "Warehouse A",
  "warrantyMonths": 24
}
```

## Run Locally

Start the API and PostgreSQL:

```bash
docker compose up --build
```

The API runs at:

```text
http://localhost:8080
```

Swagger UI is available at:

```text
http://localhost:8080/swagger-ui.html
```

## Run Tests

```bash
./mvnw test
```

On Windows PowerShell:

```powershell
.\mvnw.cmd test
```

## Configuration

The application reads database settings from environment variables:

```text
DATABASE_URL
DATABASE_USERNAME
DATABASE_PASSWORD
```

Local defaults are provided for development, while Docker Compose supplies container-specific values.

## Project Structure

```text
src/main/java/com/kasha/productcatalog
  controller/   REST endpoints
  dto/          Request and response models
  exception/    API error handling
  model/        JPA entities
  repository/   Spring Data repositories
  service/      Business logic
```

## Future Improvements

- Add pagination and filtering
- Add Flyway database migrations
- Add Testcontainers integration tests
- Add authentication for admin-only writes
