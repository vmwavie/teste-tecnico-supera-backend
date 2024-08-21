FROM maven:3.8.4-openjdk-8

LABEL author="vmwavie"

WORKDIR /app

COPY pom.xml .
COPY src ./src

ENTRYPOINT ["mvn", "spring-boot:run"]