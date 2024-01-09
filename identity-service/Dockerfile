FROM maven:latest AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Create the final image with the compiled JAR
FROM openjdk:17-alpine
WORKDIR /app
COPY --from=build /app/target/identity-service*.jar /app/identity-service.jar
CMD ["java", "-jar", "identity-service.jar"]
EXPOSE 8083
