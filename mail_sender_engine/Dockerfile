# Build stage
FROM maven:3.9-eclipse-temurin-22 AS build

WORKDIR /app

COPY mail_sender_engine/pom.xml .
COPY mail_sender_engine/src ./src

RUN mvn clean package -Dmaven.test.skip=true

# Run stage
FROM eclipse-temurin:22-jre-jammy

# Install certificates
RUN apt-get update && apt-get install -y ca-certificates && update-ca-certificates

WORKDIR /app

# Copy the built artifact from the build stage
COPY --from=build /app/target/*.jar app.jar

EXPOSE 7005

# Add Java options for SSL debugging if needed
ENV JAVA_TOOL_OPTIONS="-Djavax.net.debug=ssl,handshake"

CMD ["java", "-jar", "app.jar"]

# FROM openjdk:22-jdk-slim
# RUN apt-get update && apt-get install -y ca-certificates && update-ca-certificates
# COPY mail_sender_engine/target/mail_sender_engine.jar app.jar
# EXPOSE 7001
# ENTRYPOINT ["java","-jar","/app.jar"]