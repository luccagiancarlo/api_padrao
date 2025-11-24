# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a Spring Boot 3.3.2 REST API (currently named `api_dw` but being transitioned to `api_padrao` under package `br.uem.vestibular.api_padrao`) that provides data warehouse endpoints for an entrance exam/contest management system. The API uses JWT authentication and connects to an IBM DB2 database.

## Build and Run Commands

**Build the project:**
```bash
mvn clean install
```

**Run the application:**
```bash
mvn spring-boot:run
```

**Run tests:**
```bash
mvn test
```

**Run a single test class:**
```bash
mvn test -Dtest=ClassName
```

**Package as JAR:**
```bash
mvn package
```

The application runs on the default Spring Boot port (8080) unless configured otherwise.

## Technology Stack

- **Java 21** - Required Java version
- **Spring Boot 3.3.2** - Framework
- **Spring Security** - JWT-based authentication with BCrypt password encoding
- **Spring Data JPA** - Database access layer
- **IBM DB2** - Database (jcc driver 11.5.8.0)
- **AWS SDK 2.25.11** - S3 integration for file storage
- **JJWT 0.11.5** - JWT token generation and validation
- **OkHttp 4.9.1** - HTTP client for external API calls

## Architecture

### Package Structure

- `controller/` - REST endpoints (all under `/api_dw/v1` base path)
- `service/` - Business logic layer
- `jpa/` - JPA entities and custom repositories
- `dto/` - Data Transfer Objects
- `utils/` - Utility classes (security, JWT, database helpers)

### Authentication Flow

The API supports multiple authentication mechanisms:

1. **Web authentication** (`/api_dw/v1/autenticar`) - Returns JWT token string
2. **App authentication** (`/api_dw/v1/autenticar_app`) - Returns detailed login response with permissions
3. **DW authentication** (`/api_dw/v1/autenticar_dw`) - Data warehouse specific auth

**Authentication implementation details:**
- Hard-coded admin user: `admlog@institutoaocp.org.br` with password `177900`
- External authentication via Lucca Software API (`https://luccasoftware.com.br/api/iaocp_auth`)
- JWT tokens expire after 10 hours
- All endpoints except auth and S3 endpoints require Bearer token authentication

**SecurityConfig** (`utils/SecurityConfig.java`):
- Uses stateless session management
- CSRF disabled
- Public endpoints: `/api_dw/v1/autenticar*`, `/api_dw/v1/presigned-url*`, `/api_dw/v1/tamanho-arquivo-s3`, `/api_dw/v1/upload-multipart`
- JwtAuthenticationFilter runs before UsernamePasswordAuthenticationFilter

### Database Architecture

**Multi-tenant design:** The database uses a dynamic table naming pattern where tables are prefixed with contest/exam names (e.g., `concurso_name_local`, `concurso_name_candidato`). This is a critical architectural decision that affects all repository implementations.

**Database access patterns:**
- Native SQL queries are used extensively due to dynamic table naming
- `EntityManager` is injected via `@PersistenceContext`
- Custom repository pattern (not extending JpaRepository) for dynamic table queries
- `DatabaseUtils` utility provides column existence checking

**Key tables:**
- `concurso` - Contest/exam metadata table (fixed name, not prefixed)
- `{prefix}_local` - Location/venue data per contest
- `{prefix}_candidato` - Candidate data per contest
- Various other `{prefix}_*` tables for events, results, etc.

### Repository Pattern

Repositories do **not** extend `JpaRepository`. Instead, they:
1. Use `@Repository` annotation
2. Inject `EntityManager` via `@PersistenceContext`
3. Execute native SQL queries with `entityManager.createNativeQuery()`
4. Manually map `Object[]` results to DTOs

Example from `LocalRepository.java:23-48`:
```java
String sql = "SELECT a.id as id_local, a.cidade, a.escola... FROM " + prefixo + "_local a...";
Query query = entityManager.createNativeQuery(sql);
List<Object[]> resultList = query.getResultList();
// Manual mapping to DTO
```

## Configuration

**Database connection** is configured in `application.properties`:
- URL: `jdbc:db2://212.85.20.149:50000/producao`
- Driver: `com.ibm.db2.jcc.DB2Driver`
- Hibernate dialect: `org.hibernate.dialect.DB2Dialect`
- DDL auto: `update`
- Show SQL: `false`

**JWT Secret:** Configured in `JwtUtil.java:16` as `SECRET_KEY` constant (should be externalized to application.properties for production)

## AWS S3 Integration

The API provides S3 file operations through presigned URLs and multipart uploads:
- Presigned URL generation for uploads
- Presigned URL generation for downloads
- File size checking in S3
- Multipart upload support

Endpoints are public (no authentication required) for S3 operations.

## Code Refactoring in Progress

The git status shows a major package refactoring from `br.com.luccasoftware.api_dw` to `br.uem.vestibular.api_padrao`. Many files have been deleted or moved. When working with this codebase:
- Use the new package structure: `br.uem.vestibular.api_padrao`
- Base artifact ID is still `api_dw` in pom.xml but may change
- Main class is `ApiDwApplication` (despite package rename)

## Development Notes

1. **Dynamic SQL construction:** Always use parameterized queries or proper escaping when building dynamic table names to avoid SQL injection
2. **Error handling:** Repositories use try-catch blocks but often just print errors; consider proper logging framework
3. **Hard-coded credentials:** Admin credentials are hard-coded in `AuthenticationService.java:37-42`; consider externalizing
4. **External API dependency:** Authentication relies on external Lucca Software API; ensure fallback handling
5. **Entity scanning:** Main application explicitly scans `br.uem.vestibular.api_padrao.jpa` for entities
