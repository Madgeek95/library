# Library Management System MVP (Spring Boot + Spring JDBC + PostgreSQL)

Настоящий production-ready MVP REST API сервис библиотеки, разработанный на **Java 17**, **Spring Boot 3**, **Spring JDBC (JdbcTemplate)** и **PostgreSQL**.

---

## 🛠 Технологический стек
* **Java 17**
* **Spring Boot 3.2.3**
* **Spring JDBC (`JdbcTemplate`)** — прямой и эффективный доступ к БД через SQL-запросы без JPA overhead.
* **PostgreSQL**
* **Jakarta Validation** (`@NotBlank`, `@Size`)
* **SpringDoc OpenAPI (Swagger UI)**
* **Docker / Docker Compose**

---

## 📁 Структура проекта
```text
library-management-system/
├── docker-compose.yml
├── pom.xml
├── README.md
└── src/
    └── main/
        ├── java/
        │   └── com/
        │       └── example/
        │           └── library/
        │               ├── LibraryApplication.java
        │               ├── controller/
        │               │   └── BookController.java
        │               ├── dao/
        │               │   └── BookDao.java
        │               ├── dto/
        │               │   └── BookRequestDto.java
        │               ├── exception/
        │               │   ├── BookNotFoundException.java
        │               │   └── GlobalExceptionHandler.java
        │               └── model/
        │                   └── Book.java
        └── resources/
            ├── application.yml
            └── schema/
                ├── data.sql
                └── schema.sql
```

---

## 🚀 Запуск проекта

### 1. Запуск PostgreSQL через Docker
```bash
docker compose up -d
```

### 2. Запуск Spring Boot приложения
```bash
./mvnw spring-boot:run
```
*(Или откройте проект в IntelliJ IDEA / Eclipse / VS Code и запустите `LibraryApplication.java`)*

---

## 📑 Интерактивная документация (Swagger UI)
После запуска приложения Swagger UI доступен по адресу:
👉 **[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)**

---

## 🔌 Эндпоинты REST API

| Метод | Эндпоинт | Описание |
|---|---|---|
| `GET` | `/api/v1/books` | Получить список всех книг (поддерживается поиск: `?title=Clean`) |
| `GET` | `/api/v1/books/{id}` | Получить книгу по ID |
| `POST` | `/api/v1/books` | Добавить новую книгу |
| `PUT` | `/api/v1/books/{id}` | Обновить данные книги |
| `PATCH` | `/api/v1/books/{id}/toggle-availability` | Изменить статус доступности (выдать/вернуть) |
| `DELETE` | `/api/v1/books/{id}` | Удалить книгу по ID |

---

## 📝 Пример JSON для создания книги (`POST /api/v1/books`)
```json
{
  "title": "Grokking Algorithms",
  "author": "Aditya Bhargava",
  "isbn": "9781617292231"
}
```
