FROM openjdk:15-jdk-alpine
ADD target/systemevents-docker.jar systemevents-docker.jar
EXPOSE 8090
ENTRYPOINT ["java", "-jar", "systemevents-docker.jar"]