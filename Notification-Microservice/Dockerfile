FROM openjdk:15-jdk-alpine
ADD target/notification-docker.jar notification-docker.jar
EXPOSE 8086
ENTRYPOINT ["java", "-jar", "notification-docker.jar"]