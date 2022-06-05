## Вездекод за 50

Для сборки проекта и его запуска требуется JDK8+ и JRE8+ соответственно
### Сборка проекта
Для Linux/MacOS
```bash
./mvnw clean package
```

Для Windows
```powershell
.\mvnw.cmd clean package
```
Итоговый файл будет находится в target/gateway-0.0.1-SNAPSHOT.jar

Для того чтобы команды запуска проекта работали следует его переименовать в backend50.jar, либо заменить backend50.jar в них на target/gateway-0.0.1-SNAPSHOT.jar

### Запуск проекта
Сначала соберите проект или скачайте backend50.jar файл с Яндекс.Диска

Также для тестирование вам будет необходимо сначала запустить на любом порту проект из backend30. Также вы можете запустить несколько backend30-проектов на разных портах для шардирования. Для сборки или запуска backend30 обратитесь к [руководству](../backend30/README.md)

Для Linux/MacOS:
```bash
env servers="localhost:8082,localhost:8083,localhost:8084" request-limit="100" limit-reset="30" java -jar backend50.jar --server.port=8081
```

Для Windows:
```powershell
cmd /C "set servers=localhost:8082,localhost:8083,localhost:8084 && java -jar backend50.jar --server.port=8081"
```

Где:
* servers="localhost:8082,localhost:8083,localhost:8084" - список адресов шардов через запятую, без http:// и /votes
* --server.port=8081 - порт для веб-сервиса (по умолчанию порт 8080)

**Все параметры кроме --server.port ОБЯЗАТЕЛЬНЫ!**