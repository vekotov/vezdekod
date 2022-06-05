## Вездекод - бэкэнд за 10
Для сборки проекта и его запуска требуется JDK8+
### Сборка проекта
Для Linux/MacOS:
```bash
./mvnw clean package
```
Для Windows:
```powershell
.\mvnw clean package
```

### Запуск проекта
Для Linux/MacOS:
```bash
env artists="foo,bar" java -jar target/backend-0.0.1-SNAPSHOT.jar --server.port=8080
```
Для Windows:
```powershell
cmd /C "set artists=foo,bar && java -jar target/backend-0.0.1-SNAPSHOT.jar --server.port=8081"
```

Где 
* artists="foo,bar" - перечисленные через запятую имена исполнителей.
* --server.port=8080 - порт для запуска веб-сервиса (по умолчанию 8080)