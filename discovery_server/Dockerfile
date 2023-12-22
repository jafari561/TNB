# Stage 1: Build the application with Maven
FROM maven:latest AS build
WORKDIR /app
COPY pom.xml .
#RUN ["mvn", "dependency:resolve"]
#RUN ["mvn", "verify"]
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Create the final image with the compiled JAR
FROM openjdk:17-alpine
WORKDIR /app
COPY --from=build /app/target/discovery_server*.jar /app/discovery_server.jar
CMD ["java", "-jar", "discovery_server.jar"]
EXPOSE 8761