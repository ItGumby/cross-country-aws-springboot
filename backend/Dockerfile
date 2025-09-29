FROM openjdk:21-jre-slim

WORKDIR /app

COPY target/cross-country-api-*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app.jar", "--spring.profiles.active=kubernetes"]
