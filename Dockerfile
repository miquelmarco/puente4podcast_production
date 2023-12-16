FROM gradle:8-jdk11-alpine

COPY . .

RUN gradle build