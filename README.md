# Measurement Tracker (v2)

Обновлённый Spring Boot сервис учёта показаний:
- User + Meter + MeterReading (нормализованная модель)
- Basic Auth + регистрация
- OCR очередь (FIFO) через таблицу `ocr_tasks` и `@Scheduled` worker
- PostgreSQL, Swagger, валидация, глобальная обработка ошибок

## Запуск
1. Поднять PostgreSQL и создать БД `measurement_tracker`.
2. Проверить `src/main/resources/application.properties`.
3. Запустить: `./mvnw spring-boot:run`
4. Swagger: `/swagger-ui/index.html`

## Новые endpoint'ы
- `POST /auth/register`
- `POST /auth/login`
- `GET /users/me`
- `POST /meters`, `GET /meters`, `GET /meters/{id}`
- `POST /readings/manual`, `GET /readings`
- `POST /ocr/tasks`, `GET /ocr/tasks/{id}`, `GET /ocr/tasks`

## OCR очередь
1) файл сохраняется в `uploads/original`
2) создаётся задача со статусом `NEW`
3) воркер обрабатывает `NEW -> PROCESSING -> DONE/FAILED`
4) при успехе создаётся `meter_readings` с `source=OCR`

## Legacy
Старый модуль `Measurement` сохранён как legacy для совместимости.
