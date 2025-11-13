# ---- Stage 1: Build ----
FROM eclipse-temurin:21 AS build

WORKDIR /app

# Copy Gradle wrapper and configuration first (for dependency caching)
COPY gradlew ./
COPY gradle gradle
COPY build.gradle settings.gradle ./

# Download dependencies (this layer will be cached)
RUN ./gradlew dependencies --no-daemon || return 0

# Copy the rest of the source code
COPY src src

# Build the Spring Boot JAR (no tests to speed up)
RUN ./gradlew bootJar --no-daemon -x test

# ---- Stage 2: Run ----
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose the default Spring Boot port
EXPOSE 8080

# Start the application
CMD ["java", "-jar", "app.jar"]
