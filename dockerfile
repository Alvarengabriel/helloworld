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

ENTRYPOINT ["/bin/bash", "-c", "java -Dserver.port=$PORT -Dspring.datasource.url=$SPRING_DATASOURCE_URL -Dspring.datasource.username=$SPRING_DATASOURCE_USERNAME -Dspring.datasource.password=$SPRING_DATASOURCE_PASSWORD -jar target/helloworld-0.0.1-SNAPSHOT.jar"]

