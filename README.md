# Library Management API

REST API for managing books, authors, members, loans, and library staff.

## Features

- JWT authentication with login and register endpoints
- Secure endpoints (all API endpoints require auth except `/api/v1/auth/**`)
- JPA/Hibernate persistence with PostgreSQL
- OpenAPI/Swagger in development profile
- Unit tests for core service logic

## Tech Stack

- Java 21
- Spring Boot 4
- Spring Web MVC
- Spring Data JPA + Hibernate
- Spring Security + JWT (`jjwt`)
- PostgreSQL
- Maven
- MapStruct + Lombok

## Environment Configuration

This project uses environment variables for database and JWT configuration.

Use `.env.template` as a starting point:

1. Copy `.env.template` to `.env`
2. Fill in your real values

Required variables:

| Variable | Description |
| --- | --- |
| `SPRING_PROFILES_ACTIVE` | Spring profile (`dev` or `prod`) |
| `DB_USERNAME` | PostgreSQL username |
| `DB_PASSWORD` | PostgreSQL password |
| `DB_NAME` | Database name (used in `dev`) |
| `DB_URL` | Full JDBC URL (used in `prod`) |
| `JWT_SECRET` | Secret key for token signing |
| `JWT_EXPIRATION` | Token expiration in milliseconds |

## Development: PostgreSQL with Docker

The `docker-compose.yaml` file starts a PostgreSQL container using values from `.env`.

1. Create `.env` from `.env.template` and set at least:
   - `DB_USERNAME`
   - `DB_PASSWORD`
   - `DB_NAME`
   - `SPRING_PROFILES_ACTIVE=dev`
   
2. Start PostgreSQL:

```bash
docker compose up -d
```

3. Run the API:

```bash
./mvnw spring-boot:run
```

In `dev`, datasource URL is resolved from:

`jdbc:postgresql://localhost:5432/${DB_NAME}`

Swagger UI is enabled in `dev`:

- `http://localhost:8080/swagger-ui/index.html`

## Production Profile (IntelliJ IDEA or Any Other Runner)

In `prod`, datasource URL is read from `DB_URL`.

Example flow:

1. Set `SPRING_PROFILES_ACTIVE=prod`
2. Provide `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`, `JWT_SECRET`, `JWT_EXPIRATION`
3. Start the app with Maven, IntelliJ, or your deployment runner

### IntelliJ IDEA

In your Run Configuration for `LibraryManagementApplication`:

1. Open `Run | Edit Configurations...`
2. Select the application run config
3. Add environment variables (`SPRING_PROFILES_ACTIVE`, `DB_URL`, etc.)
4. Run

Note: Spring Boot does not read `.env` files automatically by default. Use IntelliJ environment variables, an EnvFile plugin, shell-exported variables, or your platform secret manager.

## Security and Authentication

Security behavior:

- Public endpoints:
  - `POST /api/v1/auth/login`
  - `POST /api/v1/auth/register`
- Protected endpoints:
  - `/api/v1/books`
  - `/api/v1/authors`
  - `/api/v1/categories`
  - `/api/v1/publishers`
  - `/api/v1/book-copies`
  - `/api/v1/members`
  - `/api/v1/librarians`
  - `/api/v1/loans`

JWT usage:

1. Register a user with `POST /api/v1/auth/register`
2. Login with `POST /api/v1/auth/login`
3. Use returned bearer token in header:

```http
Authorization: Bearer <accessToken>
```

## Data Model (ER)

Entity relationship model:

- [Entity Relationship Model](https://www.image2url.com/r2/default/images/1786908071867-4edce618-e5bf-4661-b58d-fbb1be98fd8d.png)

## Testing

This project includes unit tests (JUnit + Mockito), especially for service-layer logic:

- `BookServiceTest`
- `CategoryServiceTest`
- `LoanServiceTest`

Run tests:

```bash
./mvnw test
```

## Notes

- Current profile configs use `spring.jpa.hibernate.ddl-auto=create`; this recreates schema at startup.