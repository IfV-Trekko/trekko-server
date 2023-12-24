# Build stage
FROM maven:3-eclipse-temurin-17 AS build
COPY . .
RUN mvn clean package -DskipTests

# Package stage
FROM eclipse-temurin:17-alpine
VOLUME /tmp
COPY --from=build /target/api-0.0.1-SNAPSHOT.jar api.jar
ENV PORT=8080
EXPOSE 8080
ENTRYPOINT ["java","-jar","/api.jar"]
