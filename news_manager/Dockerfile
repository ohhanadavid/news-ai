######################## for prodact ################################

FROM maven:3.9-eclipse-temurin-22 AS build

WORKDIR /app

COPY news_manager/pom.xml .
COPY news_manager/src ./src


RUN mvn clean package -Dmaven.test.skip=true

FROM openjdk:22-jdk-slim

WORKDIR /app

COPY --from=build /app/target/news_manager.jar .

EXPOSE 7002

CMD ["java", "-jar", "news_manager.jar"]

####################### foe devlope ###################################
# FROM openjdk:22-jdk-slim
# COPY news_manager/target/*.jar app.jar
# EXPOSE 7002
# ENTRYPOINT ["java","-jar","/app.jar"]
