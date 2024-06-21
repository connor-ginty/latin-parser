# Use a multi-stage build to reduce the final image size

# Stage 1: Build the application
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /latin-parser/latin-parser-app/kotlin

# Copy the pom.xml and source code
COPY pom.xml .
COPY src ./src

# Package the application
RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the packaged jar file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port the app runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
