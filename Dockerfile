FROM openjdk:17-alpine
WORKDIR /app
COPY . /app
EXPOSE 8080
CMD ["java", "-jar", "target/blog-0.0.1-SNAPSHOT-exec.jar"]
