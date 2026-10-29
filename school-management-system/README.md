# School Management System MVP (Spring Boot + Spring JDBC + PostgreSQL)

Production-ready MVP REST API сервис школьного учета и библиотеки, разработанный на базе структуры сущностей из `gitTutorial-master` (`Maktab`, `Xona`, `Student`, `Person`, `Book`, `Darslik`, `Daftar`, `Kutubxona`).

---

## 🏗 Сопоставление сущностей gitTutorial -> REST API Architecture

| Класс в `gitTutorial` | Таблица БД / Модель | Назначение |
|---|---|---|
| `Maktab` | Application Scope | Школьная система учета (`SchoolApplication`) |
| `Xona` | `rooms` | Учебные классы / кабинеты |
| `Student` / `Person` | `students` | Ученики и их личные данные |
| `Book` / `Darslik` / `Daftar` / `Kutubxona` | `books` | Школьные учебники и учет их выдачи ученикам |

---

## 🛠 Технологический стек
* **Java 17**
* **Spring Boot 3.2.3**
* **Spring JDBC (`JdbcTemplate`)**
* **PostgreSQL**
* **Jakarta Validation**
* **SpringDoc OpenAPI (Swagger UI)**
* **Docker Compose**

---

## 🚀 Запуск проекта

### 1. Поднятие PostgreSQL
```bash
docker compose up -d
```

### 2. Запуск приложения
```bash
./mvnw spring-boot:run
```

---

## 📑 Interactive Swagger UI
👉 **[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)**
