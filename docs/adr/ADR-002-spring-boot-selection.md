## ADR-002 : Why Spring Boot over Micronaut/Quarkus



## Context

Target environment is enterprise Java. Spring boot is the dominant framework in this ecosystem by hiring volume, tooling. and library support



## Decision

Use spring boot framework over other specialized frameworks. 



## Consequences

### Advantages

- Covers 80 percent of the use cases in an enterprise setting. 

- Spring's DI model is the industry baseline -- familarity with it is the prerequisite for Micronaut or Quarkus

### Disadvantages

- Spring has a bigger memory foot print and has dependency injection at run time. 

- Spring has slower boot speed. 

### Mitigation

- These don't matter at the moment as the project being built is for learning about the concepts. 

- Spring Boot 3 supports GraalVM native compilation, so if deployment constraints change (Lambda, edge), the migration path exists within the framework without a stack switch


