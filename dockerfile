FROM openjdk:17-jdk-slim-buster

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

COPY src src

RUN chmod +x mvnw

RUN ./mvnw dependency:go-offline -B
RUN ./mvnw clean install -DskipTests

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "target/helloworld-0.0.1-SNAPSHOT.jar"]