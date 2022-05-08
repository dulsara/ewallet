#FROM openjdk:8-jdk-alpine
#VOLUME /tmp
#COPY target/*.jar app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]
FROM openjdk:8-jdk-alpine
#FROM --platform=linux/amd64 openjdk:11-jdk-slim


# Refer to Maven build -> finalName
ARG JAR_FILE=target/ewallet-app-0.0.1-SNAPSHOT.jar
#EXPOSE 8080 8080
# cd /opt/app
WORKDIR /opt/app

# cp target/spring-boot-web.jar /opt/app/app.jar
COPY ${JAR_FILE} app.jar

# java -jar /opt/app/app.jar
ENTRYPOINT ["java","-jar","app.jar"]