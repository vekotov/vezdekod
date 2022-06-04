Для сборки проекта и его запуска требуется JDK 8 (1.8) и JRE 8 (1.8)
### Сборка проекта
Выполните
```bash
./mvnw clean package
```

### Запуск проекта
Выполните
```bash
env artists="foo,bar" limit-reset="60" request-limit="100" java -jar target/backend-0.0.1-SNAPSHOT.jar
```
Где: 
* artists="foo,bar" - перечисленные через запятую имена исполнителей.
* limit-reset="60" - раз во сколько секунд сбрасывается лимит на голосование
* request-limit="100" - размер лимита на голосование (сколько раз можно проголосовать до ошибки 429)

Порт проекта по умолчанию - 8080.