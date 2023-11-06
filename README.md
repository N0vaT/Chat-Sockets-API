# Chat-Sockets API
**Реализация:**
- Java — 1.8;
- spring-context
  postgresql
  spring-jdbc
  spring-security-core
  HikariCP

**Структура проекьа**:
- Chat
    - Client
       - src
          - main
              - java
                  - school21.Client
                      - app
                          - Main
                      - models
                          - Client
                      - threads
                          - ReadThread
                          - WriteThread
       - pom.xml 
    - SocketServer
        - src
            - main
                - java
                    - school21.sockets
                        - app
                            - Main
                        - config
                            - SocketsApplicationConfig
                        - exceptions
                            - InvalidCommandException
                        - models
                            - Message
                            - MessageMapper
                            - User
                            - UserMapper
                        - repositories
                            - CrudRepository
                            - MessagesRepository
                            - MessagesRepositoryImpl
                            - UsersRepository
                            - UsersRepositoryImpl
                        - server
                            - ClientHandler
                            - Server
                        - services
                            - MessagesService
                            - MessageServiceImpl
                            - UserService
                            - UserServiceImpl
                - resources
                    - db.properties
                    - schema.sql
        - pom.xml
    - pom.xml

Реализация многопользовательского чата.
Созданы два приложения: сокет-сервер и сокет-клиент. Сервер поддерживает подключения клиентов в многопоточном режиме 
и выполнен как отдельный проект Maven. Серверный JAR-файл запускается следующим образом:
```
$ java -jar target/socket-server.jar --port=8081
```

Клиент — это тоже отдельный проект:
```
$ java -jar target/socket-client.jar --server-port=8081
```
Чтобы обеспечить безопасное хранение паролей, используется механизм хеширования с PasswordEncoder и
BCryptPasswordEncoder (Spring Security). Бин для этого компонента описан в классе конфигурации
SocketsApplicationConfig и использован в UsersService.
Ключевая логика взаимодействия клиент/сервер и использование UsersService через контекст Spring 
реализованы в классе Server.
Использован один источник данных — HikariCP.
Работа с репозиторием реализована через JdbcTemplate.

В приложении обеспечен многопользовательский обмен сообщениями.
Оно поддерживало следующий жизненный цикл пользователя чата:
- Регистрация
- Вход в систему (если пользователь не обнаружен, соединение закроется)
- Отправка сообщений (каждый пользователь, подключенный к серверу, получает и может отправлять сообщения)
- Переписка сохраняется в БД.
- Выход.

Пример работы приложения на стороне клиента:

```
Hello from Server!
> signUp
Enter username:
> Marsel
Enter password:
> qwerty007
Successful!


Hello from Server!
> signIn
Enter username:
> Marsel
Enter password:
> qwerty007
Start messaging
> Hello!
Marsel: Hello!
NotMarsel: Bye!
> END
```