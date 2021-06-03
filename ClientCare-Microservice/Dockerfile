FROM openjdk:15-jdk-alpine
ADD target/clientcare-docker.jar clientcare-docker.jar
EXPOSE 8087
ENTRYPOINT ["java", "-jar", "clientcare-docker.jar"]