# Use an official Maven image to build the project with Java 17
FROM maven:3.8.4-openjdk-17 AS build

# Set working directory inside the container
WORKDIR /app

# Copy the project files into the container
COPY oakcollectionsadmin/ .

# Give execution rights on the mvnw file
RUN chmod +x mvnw

# Run Maven build with the wrapper
RUN ./mvnw clean install -DskipTests

# Use a lightweight Java runtime for the production environment
FROM openjdk:17-jre-slim

# Set working directory inside the container
WORKDIR /app

# Copy the built artifact from the build stage
COPY --from=build /app/target/oakcollectionsadmin-*.jar oakcollectionsadmin.jar

# Expose port 8080 for the app
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "/app/oakcollectionsadmin.jar"]
