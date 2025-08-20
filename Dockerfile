FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY target/*.jar skillmatch.jar

ENTRYPOINT ["java", "-jar", "skillmatch.jar"]
