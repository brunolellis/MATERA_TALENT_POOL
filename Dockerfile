FROM openjdk:8-jre-slim

ADD target/employee-api.jar employee-api.jar

ENTRYPOINT ["java","-jar","employee-api.jar"]