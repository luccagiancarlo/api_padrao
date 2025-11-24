# Repository Guidelines

## Project Structure & Module Organization
- Java source lives in `src/main/java/br/uem/vestibular/api_padrao` with typical Spring layers: `controller` (REST endpoints), `service` (auth/user logic), `dto` (request/response payloads), `jpa` (entities and repositories), and `utils` (JWT, CSV export, AWS S3 helpers, security config).
- Shared configuration and templates sit in `src/main/resources` (`application.properties`, `static/`, `templates/`). Use `application.properties.example` as the starting point for local secrets.
- Tests live in `src/test/java/br/uem/vestibular/api_padrao`; build outputs land in `target/`.

## Build, Test, and Development Commands
- `./mvnw clean verify` — full compile, tests, and packaging to `target/`.
- `./mvnw test` — run the JUnit/Jupiter test suite.
- `./mvnw spring-boot:run` — start the API locally (reads `application.properties`).
- `docker build -t api-padrao .` then `docker run -p 8080:8080 api-padrao` for containerized runs.

## Coding Style & Naming Conventions
- Java 21, Spring Boot 3.3.x; prefer 4-space indentation and trim trailing whitespace.
- Classes in `PascalCase`, methods/fields in `camelCase`; request/response DTOs end with `Request`/`Retorno` and controllers end with `Controller`.
- Keep controllers thin, push business logic into services; utilities stay stateless.
- Run `./mvnw fmt:format` if the formatter is configured locally; otherwise keep code consistent with existing style.

## Testing Guidelines
- Framework: Spring Boot Test with JUnit 5 (starter dependency). Favor `@SpringBootTest` only when integration coverage is needed; otherwise prefer slice/unit tests.
- Name test classes `*Tests` and methods `shouldDoX_whenConditionY`.
- Add tests for new endpoints, JWT/authorization paths, and persistence queries. Use in-memory configs or test doubles instead of real DB2/S3.

## Commit & Pull Request Guidelines
- Follow the existing short, imperative commit style (often Portuguese), e.g., `Ajusta autenticação JWT` or `Corrige busca de usuários`.
- Each PR should include: a brief summary, steps to reproduce/verify, linked issue (if any), and screenshots or sample curl requests for new endpoints.
- Run `./mvnw test` (and `./mvnw clean verify` before release) before opening PRs; note any failing tests and why.

## Security & Configuration Tips
- Never commit real credentials or keys; copy `application.properties.example` and set secrets via environment or profiles (e.g., `application-prod.properties` for deployments).
- JWT secrets, DB2 credentials, and AWS S3 keys must be provided through env vars or secure config stores.
- Check that CORS, auth filters, and `SecurityConfig` changes are reviewed when touching endpoints.
