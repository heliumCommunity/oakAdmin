FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copy Maven wrapper scripts
COPY mvnw .
COPY .mvn/ .mvn/

# Copy project files (note the nested path)
COPY oakcollectionsadmin/pom.xml .

# Download dependencies first
RUN chmod +x mvnw && ./mvnw dependency:go-offline -B

# Copy source code (nested path)
COPY oakcollectionsadmin/src ./src

# Build the app
RUN ./mvnw clean package -DskipTests

# Expose the port Spring Boot runs on
EXPOSE 8080

# Run the built jar
CMD ["java", "-jar", "target/*.jar"]
