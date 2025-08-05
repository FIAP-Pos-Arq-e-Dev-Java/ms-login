FROM maven:3.9.9-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY . .
RUN ./gradlew clean build

FROM alpine/java:21-jre
WORKDIR /app
COPY --from=build /app/build/libs/ms-login-*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
