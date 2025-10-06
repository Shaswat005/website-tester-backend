# Use Maven + JDK image
FROM maven:3.9.2-eclipse-temurin-17 AS build

# Set working directory
WORKDIR /app

# Copy everything
COPY pom.xml .
COPY src ./src

# Build Spring Boot JAR
RUN mvn clean package -DskipTests

# Use a lightweight JDK image to run the app
FROM eclipse-temurin:17-jdk

WORKDIR /app

# Copy built JAR from build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port
EXPOSE 8080

# Run the JAR
CMD ["java", "-jar", "app.jar"]
