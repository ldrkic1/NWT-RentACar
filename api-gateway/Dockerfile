FROM openjdk:15-jdk-alpine
ADD target/apigateway-docker.jar apigateway-docker.jar
EXPOSE 8089
ENTRYPOINT ["java", "-jar", "apigateway-docker.jar"]