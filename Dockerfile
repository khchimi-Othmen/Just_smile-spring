# Use the official OpenJDK base image with Java 17
FROM openjdk:17-jdk

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the host to the container
COPY target/just_smile-0.0.1-SNAPSHOT.jar /app/just_smile.jar

# Expose the port that your Spring Boot application listens on (change as needed)
EXPOSE 8088

# Command to run your Spring Boot application
CMD ["java", "-jar", "ms-bank_branch.jar"]
