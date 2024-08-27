# Use the official Maven image as a build stage
FROM maven:3.8.4-openjdk-17 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the project files and dependencies
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean package

# Use a lightweight Alpine image as the base image for the final stage
FROM openjdk:17-alpine

ENV DB_USERNAME=school_site_gajv_user
ENV DB_PASSWORD=HuxKdWQ37bhUT91uTAkfK85xbHz27uPf

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the build stage to the final stage
COPY --from=build /app/target/blog-0.0.1-SNAPSHOT-exec.jar ./app.jar

# Expose the port used by the application
EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "app.jar"]