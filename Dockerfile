# Use Java 21 image
FROM eclipse-temurin:21-jdk

# Create working directory inside container
WORKDIR /app

# Copy the generated JAR into container
COPY target/SpringBootWithJDBC-0.0.1-SNAPSHOT.jar app.jar

# Expose Spring Boot port
EXPOSE 8081

# Run the application
ENTRYPOINT ["java","-jar","app.jar"]