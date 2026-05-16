# measurement-tracker-clean
Clean Spring Boot project for meter readings with JWT auth, meters, manual readings, OCR task queue (FIFO), PostgreSQL, Swagger and Thymeleaf pages.

## Run
1. Create DB `measurement_tracker_clean` in PostgreSQL.
2. Configure `src/main/resources/application.properties`.
3. Run `mvn spring-boot:run`.
4. Open Swagger: `/swagger-ui/index.html`.
5. Open frontend: `/`, `/ui/login`, `/ui/register`, `/ui/meters`, `/ui/readings`, `/ui/ocr`.

## Main entities
- User
- Meter
- MeterReading
- OcrTask

## API
- POST `/auth/register`, POST `/auth/login`, GET `/users/me`
- CRUD `/meters`
- POST `/readings/manual`, GET `/readings`, GET `/readings/{id}`, GET `/readings/meter/{meterId}`, GET `/readings/history`
- POST `/ocr/tasks`, GET `/ocr/tasks`, GET `/ocr/tasks/{id}`

## OCR queue
Upload -> save file (`uploads/original`) -> create `OcrTask(NEW)` -> scheduler processes FIFO -> DONE/FAILED -> on DONE create `MeterReading`.
