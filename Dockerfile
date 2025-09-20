FROM openjdk:17

# Copy compiled classes into the container
COPY ./target/classes /app/classes

# Set the working directory
WORKDIR /app/classes

# Run your main class
ENTRYPOINT ["java", "com.napier.sem.App"]


