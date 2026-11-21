# Finance Tracker — User Service

Spring Boot service for user profiles. Part of the Finance Tracker application (Kafka + MySQL + Kong).

## Stack

- Java / Spring Boot
- MySQL
- Kafka (consumes user events from auth)

## Setup

```bash
cp .env.example .env
# edit .env with your MySQL and Kafka hosts
./gradlew bootRun
```

Default port: see `src/main/resources/application.properties`.

## Env

Copy `.env.example` → `.env`. Never commit `.env`.
