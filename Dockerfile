# ─── BUILD STAGE ───────────────────────────────────────────────────────
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

# Copy gradle configuration assets
COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./

# Ensure standard linux line endings and permissions for gradle wrapper wrapper
RUN chmod +x gradlew

# Copy application source code logic
COPY src src

# Compile application artifacts and skip tests to accelerate deployment tracking
RUN ./gradlew build -x test --no-daemon

# ─── RUN STAGE ─────────────────────────────────────────────────────────
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copy compiled jar payload execution wrapper from build layer
COPY --from=build /app/build/libs/*-SNAPSHOT.jar app.jar

EXPOSE 8082

# Run Spring Boot process using explicit production environment profile variables
ENTRYPOINT ["java", "-jar", "app.jar"]