# ADR-001: Use In-Memory Storage for Phase 1

## Context

The Phase 1 goal is demonstrating Java, Spring Boot, and REST API fundamentals —
not building production-grade persistence. Introducing a database at this stage
adds infrastructure setup, ORM configuration, and schema management overhead
that is orthogonal to those goals. The `ProductRepository` interface was
deliberately defined as an abstraction over storage, not tied to any specific
implementation.

## Decision

Use an in-memory `List`-backed implementation of `ProductRepository` for Phase 1.
No database, no ORM, no schema.

## Consequences

- Eliminates persistence infrastructure from the learning surface entirely.
- All business logic, REST endpoints, and test coverage are functional without
  a running database.
- State does not survive application restarts — acceptable for this phase.
- The `ProductRepository` interface boundary means a JPA-backed implementation
  can replace `InMemoryProductRepository` in Phase 2 with no changes to
  `ProductService` or the controller layer. This was a deliberate design choice,
  not an accidental property.


