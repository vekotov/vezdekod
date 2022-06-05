## Вездекод за 30

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
Итоговый файл будет находится в target/backend-0.0.1-SNAPSHOT.jar

Для того чтобы команды запуска проекта работали следует его переименовать в backend30.jar, либо заменить backend30.jar в них на target/backend-0.0.1-SNAPSHOT.jar

### Запуск проекта
Сначала соберите проект или скачайте backend30.jar файл с Яндекс.Диска

Для Linux/MacOS:
```bash
env artists="foo,bar" request-limit="100" limit-reset="30" java -jar backend30.jar --server.port=8081
```

Для Windows:
```powershell
cmd /C "set artists=foo,bar && set request-limit=100 && set limit-reset=30 && java -jar backend30.jar --server.port=8081"
```

Где:
* artists="foo,bar" - перечисленные через запятую имена исполнителей.
* request-limit="100" - максимальное количество запросов за указанный промежуток времени (x-ratelimit-limit)
* limit-reset="30" - раз во сколько секунд сбрасывается лимит на количество запросов (x-ratelimit-reset)
* --server.port=8081 - порт для веб-сервиса (по умолчанию порт 8080)

**Все параметры кроме --server.port ОБЯЗАТЕЛЬНЫ!**