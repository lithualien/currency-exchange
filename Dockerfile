FROM openjdk:8
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} currency-exchanger-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/currency-exchanger-0.0.1-SNAPSHOT.jar"]