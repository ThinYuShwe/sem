FROM openjdk:17

# Copy compiled classes into the container
COPY ./target/sem2025-1.0-jar-with-dependencies.jar /tmp

# Set the working directory
WORKDIR /tmp

# Run your main class
ENTRYPOINT ["java", "-jar", "sem2025-1.0-jar-with-dependencies.jar"]


