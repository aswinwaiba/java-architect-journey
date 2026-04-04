## ADR-003: Why records for the Product model.

## Context

Product type is a data carrier with no behaviour. 

It is passed between layers and serliaze to JSON. 

The question was whether to model it as a Java record or a Lombok `@Value` class.



## Decision

Use of records instead of  a @Value lombok class



## Consequences

### Advantages

- Records enforce data carrier contract at the language level. No behavior can be added accidently. 

- A Lombok `@Value` class allows methods to be added; a record structurally prevents it.

- Records are supported natively by the language Java 16+. 

- Records compose cleanly with sealed interfaces — the `Product` hierarchy gains compile-time exhaustiveness without additional ceremony

### Disadvantages

- Records don't have inheritance and no mutable state. 

- Complex workflow in the future which may requrie modifying stored values will need rework. 

- If Product needs to evolve to have optional fields or support creation patters such as builder, clone etc 

### Mitigation

- `Product` is used only as a data carrier in Phase 1.

-  If Phase 2 requires JPA mapping or a builder pattern, the migration path is a class replacement at one location — the `model` package — with no changes to service or controller contracts.
