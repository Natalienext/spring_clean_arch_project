# Music Search Portal

Учебный pet-проект: платформа объявлений для музыкантов на Spring Boot, построенная как микросервисная система с чистой архитектурой (Clean Architecture), gRPC-взаимодействием между сервисами и событийной синхронизацией данных через Kafka.

## О проекте (RU)

**Music Search Portal** — сервис объявлений, где музыканты находят друг друга: публикуют посты по жанру, локации и типу (например, поиск участников группы или предложение выступления), а другие пользователи откликаются на них.

Проект состоит из двух независимых сервисов:

- **[user-service](user-service)** — управление профилями пользователей (REST API), отдаёт данные о пользователе другим сервисам по **gRPC**, публикует события об изменениях в **Kafka**.
- **[post-service](post-service)** — управление объявлениями и откликами (REST API), запрашивает данные автора у user-service по gRPC и подписан на Kafka-события для обновления кэшированной информации об авторе.

Оба сервиса хранят данные в **MongoDB** и спроектированы по слоям чистой архитектуры: `domain` → `boundary` → `adapter` → `infra`.

### Технический стек

| Категория | Технологии |
|---|---|
| Язык / платформа | Java 17, Maven |
| Фреймворк | Spring Boot 3.5.7 (Web, Data MongoDB, Kafka) |
| Межсервисное взаимодействие | gRPC 1.70.0 + Protocol Buffers (генерация кода из `.proto` через `protobuf-maven-plugin`) |
| Событийная шина | Apache Kafka + Zookeeper (публикация/подписка на изменения пользователя) |
| База данных | MongoDB (Spring Data MongoDB) |
| Документация API | springdoc-openapi / Swagger UI (post-service) |
| Прочее | Lombok, UUID Creator (f4b6a3), Spotless + google-java-format (автоформатирование кода) |
| Тестирование | JUnit / Spring Boot Test, GripMock — мок gRPC-сервера user-service для изолированного тестирования post-service |
| Инфраструктура | Docker Compose (оркестрация всех сервисов), health checks |

### Запуск
```bash
docker-compose up
```
- user-service: REST `:8080`, gRPC `:9090`
- post-service: REST `:8081`

---

## About the project (EN)

**Music Search Portal** is a classifieds-style platform for musicians: users publish posts by genre, location and type (e.g. looking for band members or offering a gig), and others reply to them.

The project consists of two independent services:

- **[user-service](user-service)** — manages user profiles (REST API), exposes user data to other services over **gRPC**, and publishes change events to **Kafka**.
- **[post-service](post-service)** — manages posts and replies (REST API), fetches author data from user-service over gRPC, and consumes Kafka events to keep the cached author info up to date.

Both services persist data in **MongoDB** and follow Clean Architecture layering: `domain` → `boundary` → `adapter` → `infra`.

### Tech stack

| Category | Technologies |
|---|---|
| Language / build | Java 17, Maven |
| Framework | Spring Boot 3.5.7 (Web, Data MongoDB, Kafka) |
| Inter-service communication | gRPC 1.70.0 + Protocol Buffers (code generated from `.proto` via `protobuf-maven-plugin`) |
| Event bus | Apache Kafka + Zookeeper (publish/subscribe on user changes) |
| Database | MongoDB (Spring Data MongoDB) |
| API docs | springdoc-openapi / Swagger UI (post-service) |
| Other | Lombok, UUID Creator (f4b6a3), Spotless + google-java-format (code auto-formatting) |
| Testing | JUnit / Spring Boot Test, GripMock — mock gRPC server for user-service used to test post-service in isolation |
| Infrastructure | Docker Compose (orchestrates all services), health checks |

### Running locally
```bash
docker-compose up
```
- user-service: REST `:8080`, gRPC `:9090`
- post-service: REST `:8081`
