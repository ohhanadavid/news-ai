######################## for prodact ################################

#FROM maven:3.9-eclipse-temurin-22 AS build
#
#WORKDIR /app
#
#COPY user_accessor/pom.xml .
#COPY user_accessor/src ./src
#
#
#RUN mvn clean package -Dmaven.test.skip=true
#
#FROM openjdk:22-jdk-slim
#
#WORKDIR /app
#
#COPY --from=build /app/target/user_accessor.jar .
#
#EXPOSE 7002
#
#CMD ["java", "-jar", "user_accessor.jar"]

####################### foe devlope ###################################
 FROM openjdk:22-jdk-slim

 WORKDIR /app

 COPY target/*.jar app.jar

 COPY .env .env

 EXPOSE 7002

 ENTRYPOINT ["java", "--enable-preview","-jar","app.jar"]
