# Use JDK image to build & run
FROM eclipse-temurin:17-jdk

# Set working directory
WORKDIR /app

# Copy Maven wrapper and pom.xml first (caches dependencies)
COPY mvnw pom.xml ./
COPY .mvn .mvn

# Make sure mvnw is executable
RUN chmod +x mvnw

# Download dependencies
RUN ./mvnw dependency:go-offline -B

# Copy the rest of the project
COPY . .

# Build the Spring Boot JAR (skipping tests)
RUN ./mvnw clean package -DskipTests

# Expose port
EXPOSE 8080

# Run the built JAR
CMD ["sh", "-c", "java -jar target/*.jar"]
