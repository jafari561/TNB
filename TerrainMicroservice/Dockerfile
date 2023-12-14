# Stage 1: Build the application with Maven
FROM maven:3.6.3 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Create the final image with the compiled JAR
FROM openjdk:17-alpine
WORKDIR /app
COPY --from=build /app/target/terrain-service*.jar /app/terrain-service.jar
CMD ["java", "-jar", "terrain-service.jar"]
EXPOSE 8084