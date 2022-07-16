# Запуск серверной части веб-приложения

## Предусловие

- Скачать проекты из репозитория
- Открыть каждый проект по отдельности в IDE IntelliJ Idea
- Дождаться установки всех зависимостей Gradle
### Установить Java 17:
- File -> Project Structure -> Project -> SDK -> Add SDK... -> Version: 17 -> Vendor: Amazon Correto 17.0.3 -> Download
![ProjectSettings](https://github.com/bakapolice/images/blob/main/project_settings/Pr_Structure_1.jpg)
![ProjectSettings](https://github.com/bakapolice/images/blob/main/project_settings/Pr_Structure_2.jpg)

### Настроить модули, зависимости и версии языка:
- File -> Project Structure -> Project -> Language Level: Defauld SDK (17)
![ProjectSettings](https://github.com/bakapolice/images/blob/main/project_settings/Pr_Structure_3.jpg)
- File -> Project Structure -> Modules -> Dependencies -> Module SDK: Project SDK 17
![ProjectSettings](https://github.com/bakapolice/images/blob/main/project_settings/Pr_Structure_4.jpg)
- File -> Settings -> Build, Execution, Deployment -> Build Tools -> Gradle -> Gradle JVM: Project SDK 17
![ProjectSettings](https://github.com/bakapolice/images/blob/main/project_settings/Settings_1.jpg)
- File -> Settings -> Build, Execution, Deployment -> Compiler -> Java Compiler -> Project bytecode version: 17
![ProjectSettings](https://github.com/bakapolice/images/blob/main/project_settings/Settings_2.jpg)

### Настроить конфигуратор запуска:
- Run -> Edit Configurations... -> Spring Boot -> <название текущего конфигуратора> -> Build and run: java 17
![ProjectSettings](https://github.com/bakapolice/images/blob/main/project_settings/Run_config.jpg)

### Настроить соединение с БД для обоих проектов (если БД уже настроена по первой инструкции)
- View -> Tool Windows -> Database
- Нажать на значок "+"
- Data Source -> PostgreSQL
![ProjectSettings](https://github.com/bakapolice/images/blob/main/project_settings/Databes.jpg)
- В настройках подключения указываем название БД -> Database: messenger
- Authentication: User & Password
- User: postgres (который был задан при установке БД)
- Password: password (который был задан при установке БД)
- Нажать Test Connection
- Если появилась ошибка с предложением установить драйвер, то устанавливаем драйвер
- Если подключение прошло успешно, нажимаем Apply и закрываем окно настроек подключения
![ProjectSettings](https://github.com/bakapolice/images/blob/main/project_settings/Databes_2.jpg)

Все настройки сделаны

## Запуск
- Запустить main файлы каждого проекта
    - server\auth-service\src\main\java\com\example\messenger\MessengerApplication.java
    - server\chat-service\src\main\java\com\example\chatservice\ChatServiceApplication.java

Если приложения были запущены без ошибок, можно пользоваться веб приложением
