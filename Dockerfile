FROM openjdk:17.0.1-jdk

ARG JAR_FILE

COPY target/$JAR_FILE /app.jar

EXPOSE 8080

ENV SPRING_PROFILES_ACTIVE=custom

CMD ["java",  "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}", "-jar", "/app.jar"]
