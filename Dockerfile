FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY target/just_smile-1.0.0.jar /app/just_smile.jar
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
EXPOSE 8088
CMD ["java", "-jar", "just_smile.jar"]
