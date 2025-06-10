##  Measurement Tracker

Spring Boot приложение для мониторинга расхода газа, холодной и горячей воды.
Основная цель — создать REST API с поддержкой валидации, защитой от дубликатов и тестами.

---

##  Особенности
* Добавление показаний: `POST /measurements` (ответ `201 Created`)
* История измерений по `userId`: `GET /measurements/{userId}`
* Валидация входных данных
* Игнорирование дубликатов (userId + timestamp)
* Юнит- и интеграционные тесты (JUnit, Mockito, MockMvc)

---

##  Технологии

* Java 21
* Spring Boot
* Spring Web
* Spring Data JPA
* HSQLDB (in-memory)
* JUnit 5, Mockito
* MockMvc

---

##  Старт

```bash
# Склонируйте проект
https://github.com/Gooocha/measurement-tracker.git

# Откройте в IntelliJ IDEA
# Или запустите из консоли
./mvnw spring-boot:run
```

---

##  Запросы

###  POST /measurements

```json
{
  "userId": 1,
  "timestamp": "2025-05-28T10:00:00",
  "gas": 12.5,
  "coldWater": 12.0,
  "hotWater": 14.2
}
```

**Ответ**: `201 Created` или `400 Bad Request`

---

##  GET /measurements/1

```json
[
  {
    "userId": 1,
    "timestamp": "2025-05-28T10:00:00",
    "gas": 12.5,
    "coldWater": 12.0,
    "hotWater": 14.2
  }
]
```

---

##  Тесты

* `MeasurementServiceTest` — логика отсева дубликатов
* `MeasurementControllerTest` — валидация, реальное сохранение/чтение из бд

```bash
./mvnw test
```

---

##  Структура

```
src/
├── controller           // MeasurementController
├── dto                  // MeasurementRequest
├── model                // Measurement (Entity)
├── repository           // MeasurementRepository
├── service              // Service + Impl
└── test/                // Unit + Integration tests
```

---

##  Автор

Ковалев Георгий
