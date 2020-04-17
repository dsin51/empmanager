FROM openjdk:8-jdk-alpine
COPY target/*.jar empmgr.jar
ENTRYPOINT ["java", "-jar", "/empmgr.jar"]