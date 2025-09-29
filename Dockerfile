FROM openjdk:17

# Copy any jar with -jar-with-dependencies.jar
COPY ./target/*-jar-with-dependencies.jar /app.jar

WORKDIR /
ENTRYPOINT ["java", "-jar", "/app.jar"]
