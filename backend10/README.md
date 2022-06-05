## Вездекод за 10

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

Для того чтобы команды запуска проекта работали следует его переименовать в backend10.jar, либо заменить backend10.jar в них на target/backend-0.0.1-SNAPSHOT.jar

### Запуск проекта
Сначала соберите проект или скачайте backend10.jar файл с Github-релиза

Для Linux/MacOS:
```bash
env artists="foo,bar" java -jar backend10.jar --server.port=8081
```

Для Windows:
```powershell
cmd /C "set artists=foo,bar && java -jar backend10.jar --server.port=8081"
```

Где:
* artists="foo,bar" - перечисленные через запятую имена исполнителей.
* --server.port=8081 - порт для веб-сервиса (по умолчанию порт 8080)

**Все параметры кроме --server.port ОБЯЗАТЕЛЬНЫ!**