######################## for prodact ################################
 FROM openjdk:22-jdk-slim

 WORKDIR /app

 COPY target/*.jar app.jar

 COPY .env .env

 EXPOSE 7005

 ENTRYPOINT ["java", "--enable-preview","-jar","app.jar"]
