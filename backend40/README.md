## Вездекод за 40

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
Итоговый файл будет находится в target/target/backend40-0.0.1-SNAPSHOT-jar-with-dependencies.jar

Для того чтобы команды запуска проекта работали следует его переименовать в backend40.jar, либо заменить backend40.jar в них на target/target/backend40-0.0.1-SNAPSHOT-jar-with-dependencies.jar

### Запуск проекта
Сначала соберите проект или скачайте backend40.jar файл с Github-релиза

Также для тестирование вам будет необходимо сначала запустить на любом порту проект из backend30. Для этого обратитесь к [руководству](../backend30/README.md)

Для Linux/MacOS:
```bash
java -jar backend40.jar -c 10 -n 100 -a foo,bar localhost:8080
```

Для Windows:
```powershell
cmd /C "java -jar backend40.jar -c 10 -n 100 -a foo,bar localhost:8080"
```

Где:
* -с 10 - количество клиентов, одновременно голосующих на тестируемом сервисе
* -n 100 - суммарное количество запросов от них (минимальное количество - 100 (из за перцентиля в выводе))
* -a foo,bar - имена исполнителей, за которых будут голосовать клиенты
* localhost:8080 - адрес тестируемого сервиса (без http:// и /votes)

**Все параметры ОБЯЗАТЕЛЬНЫ!**