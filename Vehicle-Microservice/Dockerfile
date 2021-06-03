FROM openjdk:15-jdk-alpine
ADD target/vehicle-docker.jar vehicle-docker.jar
EXPOSE 8088
ENTRYPOINT ["java", "-jar", "vehicle-docker.jar"]