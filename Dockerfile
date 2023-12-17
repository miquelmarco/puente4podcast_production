FROM gradle:8-jdk11-alpine

COPY . .

RUN gradle build

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "build/libs/puente4podcast-0.0.1-SNAPSHOT.jar"]