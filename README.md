# congestion_calculator

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

The project uses Postgres to store the Filtering and Charging rules, so you need to start it with `docker compose up` in a separate shell or with `docker compose -d` as a background process.

You can run your application in dev mode that enables live coding using:
```shell script
./gradlew quarkusDev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Example request

```
curl --data '{"vehicleType": "Car", "crossingTimestamps": ["2013-01-14T21:00:00","2013-01-15T21:00:00","2013-02-07T06:23:27","2013-02-07T15:27:00","2013-02-08T06:27:00","2013-02-08T06:20:27","2013-02-08T14:35:00","2013-02-08T15:29:00","2013-02-08T15:47:00","2013-02-08T16:01:00","2013-02-08T16:48:00","2013-02-08T17:49:00","2013-02-08T18:29:00","2013-02-08T18:35:00","2013-03-26T14:25:00","2013-03-28T14:07:27"]}' \
    -H 'Content-Type: application/json' \
    localhost:8080/congestion-tax/calculate
```

Response: `{"taxByDate":{"2013-02-08":60,"2013-02-07":21,"2013-03-26":8},"totalTax":89}`

## Code structure

The code is structured in 3 layers: Domain, Implementation, API.

The goal of the Domain is to contain business logic with abstract components represented as interfaces.

The Implementation contain actual implementation of the abstract components in the Domain.

The API should provide http api to integrate to the service and do credential verification. It should also convert domain specific errors into suitable http errors.

Ideally the files in the domain package should not have any annotations, but this seems unfeasible without using some sort of data transformation library, which is outside of the time scope for this project.

## Main concepts

- Use `records` to represent data.
- Use `sealed classes` to represent "enumerations" (sum-types) and rely on the compiler to force you to do exhaustive checks.
- Use expressive data transformations with Java Stream.
- Represent the congestion tax rules as sealed interfaces `FilterRule` and `ChargeRule` with `apply` method.
- Represent the tollgate crossing and taxation events with records `ChargeEvent` and `TaxEvent`.
- Pipe the `CrossingEvents` through all `FilterRules` and the result through all `ChargeRules` which yields `TaxEvents` per date. Then run a final aggregation / summing on the `TaxEvents` and show the result.
- The rules are stored in Postgres as json blobs, so that other content management systems can modify them.

## What is missing from the project

- Database migrations (Flyway)
- OpenAPI specification and Swagger UI
- Observability / monitoring (some way to export metrics to Prometheus / VictoriaMetrics)
- Logging library
- Load configuration through environment variables
- Dockerfile / proper containerization (Quarkus should cover this, but I've not checked it)
- Input validation mechanism
- Data transformation library (something like Chimney, but for Java)
- Compile-time DI - May be this is not possible with Java?
- Better ways to deal with `nulls`. The intellij annotations and `Optional` leave much to be desired.

# Post-interview notes

- It seems the interviewer was inclined to see some pre-historic Java.
- Sealed interfaces and records (any kind of ADTs) were deemed "too complicated" (by the interviewer).
- Data transformations with Stream seem to be "too complicated" (for the interviewer).
- Postgres has `json` and `jsonb` column type support for over a decade now, but this is still news for people :-/
- I can't believe people have the audacity to claim that Java is "almost like Scala" nowadays. Java is still very primitive and the average Java programmer would not recognize or leverage the powers stemming from FP and Scala at all.
- You can write Java with **less** OOP nowadays and the language has **improved** and is **usable**.
