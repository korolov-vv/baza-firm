# ---- Stage 1: Build ----
FROM eclipse-temurin:21 AS build

WORKDIR /app

# Copy Maven files (for caching dependencies)
COPY pom.xml ./
COPY mvnw ./
COPY .mvn .mvn/

# Download dependencies (cached)
RUN ./mvnw dependency:resolve

# Copy source code
COPY src src

# Build the Spring Boot JAR
RUN ./mvnw package

# ---- Stage 2: Run ----
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy built JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port your Spring Boot app runs on (default: 8080)
EXPOSE 8080

# Start the application
CMD ["java", "-jar", "app.jar"]