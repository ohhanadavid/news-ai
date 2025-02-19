
 FROM openjdk:22-jdk-slim

 WORKDIR /app

 COPY target/*.jar app.jar


 EXPOSE 8080

 ENTRYPOINT ["java", "--enable-preview","-jar","app.jar"]
