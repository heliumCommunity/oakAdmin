# Use a lightweight Java 17 JDK base image
FROM eclipse-temurin:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy Maven Wrapper support files
COPY .mvn/ .mvn/
COPY mvnw .
COPY pom.xml .

# Download dependencies first to cache layers efficiently
RUN chmod +x mvnw && ./mvnw dependency:go-offline -B

# Copy the actual source code
COPY src ./src

# Build the Spring Boot app
RUN ./mvnw clean package -DskipTests

# Expose the app port
EXPOSE 8080

# Run the JAR file
CMD ["java", "-jar", "target/*.jar"]
