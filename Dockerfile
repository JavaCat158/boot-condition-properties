FROM openjdk:17-jdk-alpine
LABEL authors="JavaCat"
EXPOSE 8080
ADD target/task-boot-0.0.1-SNAPSHOT.jar myapp.jar
ENTRYPOINT ["java", "-jar", "/myapp.jar"]