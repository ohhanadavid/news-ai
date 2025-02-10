#FROM maven:3.9-eclipse-temurin-22 AS build
#
#WORKDIR /app
#
#COPY data_manager/pom.xml .
#COPY data_manager/src ./src
#
#RUN mvn clean package -Dmaven.test.skip=true
#
#FROM openjdk:22-jdk-slim
#
#WORKDIR /app
#
#COPY --from=build /app/target/data_manager.jar .
#
#EXPOSE 7001
#
#CMD ["java", "-jar", "data_manager.jar"]

####################### foe devlope ###################################
 FROM openjdk:22-jdk-slim

 WORKDIR /app

 COPY target/data_manager.jar app.jar

 EXPOSE 7001

 ENTRYPOINT ["java", "--enable-preview","-jar","app.jar"]