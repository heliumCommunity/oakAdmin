FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copy Maven wrapper and config
COPY .mvn/ .mvn/
COPY mvnw .
COPY pom.xml .

# Download dependencies
RUN chmod +x mvnw && ./mvnw dependency:go-offline -B

# Copy application source code
COPY src/ ./src/

# Build the Spring Boot application
RUN ./mvnw clean package -DskipTests

# Expose the port your Spring Boot app runs on
EXPOSE 8080

# Run the generated JAR file
CMD ["java", "-jar", "target/*.jar"]
