# ---------- BUILD STAGE ----------

FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Copy Gradle wrapper and config

COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./

RUN chmod +x gradlew

# Copy source

COPY src src

# Build application (skip tests for Fly)

RUN ./gradlew bootJar --no-daemon -x test

# ---------- RUNTIME STAGE ----------

FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy built jar

COPY --from=build /app/build/libs/*.jar app.jar

# Fly expects the app to bind to $PORT

ENV PORT=8082

EXPOSE 8082

ENTRYPOINT ["java","-jar","/app/app.jar"]
