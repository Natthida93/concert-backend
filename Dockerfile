# Use official Java 17 image with Maven
FROM eclipse-temurin:17-jdk

# Set working directory
WORKDIR /app

# Copy Maven wrapper and project files
COPY . .

# Build the app using Maven wrapper
RUN ./mvnw clean install -DskipTests

# Expose the port your Spring Boot app uses
EXPOSE 8080

CMD ["java", "-jar", "target/concert-0.0.1-SNAPSHOT.jar"]
