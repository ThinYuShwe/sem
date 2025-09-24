#FROM openjdk:17
#
##COPY ./target/sem2025-0.1.0.2-jar-with-dependencies.jar /tmp
#COPY ./target/sem2025-0.1.2.0-jar-with-dependencies.jar /tmp
#
#WORKDIR /tmp
#ENTRYPOINT ["java", "-jar", "sem2025-0.1.2.0-jar-with-dependencies.jar"]
FROM openjdk:17

# Copy any jar with -jar-with-dependencies.jar
COPY ./target/*-jar-with-dependencies.jar /app.jar

WORKDIR /
ENTRYPOINT ["java", "-jar", "/app.jar"]
