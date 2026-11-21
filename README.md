# Finance Tracker — User Service

Spring Boot service that owns **user profiles** (name, email, phone, profile pic, etc.). It stays in sync with Auth by **consuming Kafka events** published when someone signs up, and also exposes HTTP APIs to create/update or fetch a profile.

Part of the Finance Tracker microservices app (Kong + Kafka + MySQL).

**System map:** see [ARCHITECTURE.md in Auth](https://github.com/sukeerthreddy12/FinanceTrackerApplication/blob/main/ARCHITECTURE.md)

## What this service does

- Consume auth user events from Kafka topic `user_service` and create/update MySQL profile rows
- HTTP create/update profile (`/user/v1/createUpdate`)
- HTTP get profile (`/user/v1/getUser`)
- Validate basic profile fields (e.g. email format)

It does **not** issue JWTs — that is Auth. Kong routes `/user` traffic here.

## Main HTTP API

| Method | Path | Purpose |
|--------|------|---------|
| POST | `/user/v1/createUpdate` | Create or update profile |
| GET | `/user/v1/getUser` | Fetch profile by user id |

## Kafka

| Direction | Topic | Meaning |
|-----------|-------|---------|
| Consume | `user_service` | New/updated user from Auth |

## Stack

- Java / Spring Boot
- MySQL
- Kafka consumer

## Setup

```bash
cp .env.example .env
# edit .env with your MySQL and Kafka hosts
./gradlew bootRun
```

Default port: **9810** (see `application.properties`).

## Env

Copy `.env.example` → `.env`. Never commit `.env`.

## Related repos

- [Auth](https://github.com/sukeerthreddy12/FinanceTrackerApplication) (publishes the events this service consumes)
- [Expense](https://github.com/sukeerthreddy12/FinanceTrackerExpenseService) · [DS](https://github.com/sukeerthreddy12/FinanceTrackerDsService) · [Frontend](https://github.com/sukeerthreddy12/FinanceTrackerFrontend)
