# HDFC Life Policy Ledger

Spring Boot 3 / Java 17 REST API backed by Spring Data JPA and Flyway. Run with `mvn spring-boot:run` (the default `dev` profile uses H2), or package with `mvn clean verify`.

| Method | Endpoint |
|---|---|
| GET | `/api/policies`, `/api/policies/{policyNo}`, `/api/policies/search?minPremium=20000` |
| GET | `/api/policies?status=Active`, `?type=TERM`, `?customer=Anita%20Sharma` |
| GET | `/api/policies/{policyNo}/claims` |
| POST | `/api/policies`, `/api/claims` |
| DELETE | `/api/policies/{policyNo}` |
| GET | `/swagger-ui/index.html` |

`Policy` owns the `policy_riders` join table through its `@ManyToMany` mapping; `Rider` is the inverse side. Controllers return DTOs because the persistence session is closed before serialization when open-in-view is disabled. Returning an entity with a lazy riders collection from a controller can cause `LazyInitializationException`, expose internal persistence structure, and create uncontrolled entity graphs or recursion during JSON serialization.
