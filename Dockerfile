# -----------------------------
# Stage 1: Build Spring Boot app
# -----------------------------
FROM maven:3.9.2-eclipse-temurin-17 AS build

# Set working directory
WORKDIR /app

# Copy Maven wrapper and pom.xml
COPY pom.xml .

# Copy source code (including React build in src/main/resources/static)
COPY src ./src

# Build Spring Boot JAR (skip tests)
RUN mvn clean package -DskipTests

# -----------------------------
# Stage 2: Run the Spring Boot app
# -----------------------------
FROM eclipse-temurin:17-jdk

WORKDIR /app

# Copy built JAR from build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port (Render sets PORT environment variable)
EXPOSE 8080

# Run the JAR on Render's assigned port
CMD ["sh", "-c", "java -Dserver.port=$PORT -jar app.jar"]
