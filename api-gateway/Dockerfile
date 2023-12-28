# Stage 1: Build the application with Maven
FROM maven:latest AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Create the final image with the compiled JAR
FROM openjdk:17-alpine
WORKDIR /app
COPY --from=build /app/target/api-gateway*.jar /app/api-gateway.jar
CMD ["java", "-jar", "api-gateway.jar"]
EXPOSE 8080
