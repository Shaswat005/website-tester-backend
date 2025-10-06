# Use JDK image
FROM eclipse-temurin:17-jdk

# Set working directory
WORKDIR /app

# Copy Maven wrapper and pom.xml first
COPY mvnw .
COPY pom.xml .
COPY .mvn .mvn

# Make mvnw executable (run after copying)
RUN ["chmod", "+x", "mvnw"]

# Download dependencies offline
RUN ./mvnw dependency:go-offline -B

# Copy the rest of the project
COPY . .

# Build the Spring Boot JAR
RUN ./mvnw clean package -DskipTests

# Expose port
EXPOSE 8080

# Run the built JAR
CMD ["sh", "-c", "java -jar target/*.jar"]
