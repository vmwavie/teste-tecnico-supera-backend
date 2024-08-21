# SpringBoot Crud
This is a technical test for the <b>Supera Sistemas</b> interview.

## Architecture
- [Spring Boot 2.5](https://spring.io/projects/spring-boot)
- [Java 1.8](https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html)
- [JPA+Hibernate](https://hibernate.org/)
- [Swagger](https://swagger.io/)
- [Maven](https://maven.apache.org/)
- [PostgreSql 16](https://www.postgresql.org/)
- [Docker](https://www.docker.com)

## Running

You can run this app in your favorite IDE or generate a JAR and run it through the command line.

### Prerequisites

For any mode, it's necessary to have a JDK installed. This project was written using JDK 1.8 (Java 8).

Here are the settings you need:

- `spring.datasource.url`: Link to your database to connect with PostgreSQL, like this example (jdbc:postgresql://host-ip:port/database-name).
- `spring.datasource.username`: Username of your database.
- `spring.datasource.password`: Password of your database.

These are set by default in `src/main/resources/application.properties`. You can either modify the file, adding your configuration values, or set the corresponding environment variables.

If you want to run this project in Docker, you should download Docker from the [Docker Website](https://docs.docker.com/get-started/get-docker/).

### Running through the command line
This project can be run from the project root with the command:

```sh
./mvnw clean install
```

This project can also be run with Docker using the command:
```sh
docker-compose up
```
If this command doesn't work, try the command below, as in some systems the command is:
```sh
docker compose up
```

## Documentation

There's a Swagger interface configured at http://localhost:8080/swagger-ui/index.html