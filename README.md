## Java architect journey

### What this is

This is the first-month deliverable for the Java architect's journey.

This demonstrates architectural layering, dependency injection, REST contracts, and testing strategy.

### Architecture overview

There are three layers:

* **REST API layer/Controller:** This layer translates HTTP requests into service calls. It keeps transport concerns out of business logic.

* **Service layer:** Isolates business rules from HTTP concerns, so either can change without breaking the other.

* **Repository layer:** This is the layer responsible for storage and retrieval.

A shared data type (`Product` record) is passed across all layers — owned by none of them.

These layers are wired at startup by Spring Boot's DI container — no manual construction, no factory code. `ProductController` does not instantiate `ProductService`; the container injects it via constructor.

The call direction is from Controller -> Service Layer -> Repository Layer. The reverse is illegal. The repository has no knowledge of the Service or Controller. This is what keeps persistence swappable.

### Key decisions

* **[Use of in-memory storage for repository](https://www.google.com/search?q=docs/adr/ADR-001-in-memory-storage.md):** The focus is on getting onboarded to Java and its frameworks without getting bogged down by storage complexities. It doesn't retain state after a run.

* **[Selecting the Spring Boot framework](https://www.google.com/search?q=docs/adr/ADR-002-spring-boot-selection.md):** Ecosystem dominance and hiring pool depth outweigh Micronaut's startup performance advantage at this scale.

* **[Use of Records for product model](https://www.google.com/search?q=docs/adr/ADR-003-records-for-product-model.md):** Use of a Java language feature to make a strict distinction between data and behavior. If the product needs evolution, we will have to revisit this decision.

### C++ mental shifts

The container owns object lifetimes — there is no `new ProductService()` anywhere in the codebase. Spring constructs and injects dependencies at startup; you declare what you need, not how to build it.

Records are immutable objects used as data carriers; the closest equivalent we have in C++ is a `const struct`. However, in C++, there is nothing that prevents us from extending that struct or adding behavior to it later. In Java, this is enforced at the language level — the compiler rejects any attempt to add mutable state or subclass a record.

In `@WebMvcTest`, Spring is partially active, but `ProductService` is a `@MockBean` — Mockito creates it, not the container. Constructor injection means dependencies are explicit parameters — in tests, mocks are passed directly to the constructor. No Spring context is required for unit tests.

### How to run

**Prerequisites:**

1. Java 21+

2. Maven

**Standard Maven workflow:**

Bash
    mvn compile
    mvn test
    mvn spring-boot:run

| **HTTP Method** | **Endpoint URI**          | **Request Body**      | **Response Status** | **Description**                                                              |
| --------------- | ------------------------- | --------------------- | ------------------- | ---------------------------------------------------------------------------- |
| **GET**         | `/products`               | _None_                | `200 OK`            | Retrieves a list of all products. Returns a JSON array of `Product` objects. |
| **POST**        | `/products`               | JSON `Product` object | `201 Created`       | Adds a new product to the system.                                            |
| **DELETE**      | `/products/{productName}` | _None_                | `204 No Content`    | Deletes a specific product by its name.                                      |

**Example curl commands:**

Bash
    curl -X GET http://localhost:8080/products

    curl -X POST http://localhost:8080/products \
      -H "Content-Type: application/json" \
      -d '{"name": "Wireless Mouse", "price": 25.99}'

    curl -X DELETE http://localhost:8080/products/Wireless%20Mouse
