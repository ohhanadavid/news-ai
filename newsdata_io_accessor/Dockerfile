FROM maven:3.9-eclipse-temurin-22 AS build

WORKDIR /app

 COPY newsdata_io_accessor/pom.xml .
 COPY newsdata_io_accessor/src ./src

# COPY /pom.xml .
# COPY /src ./src

RUN mvn clean package -Dmaven.test.skip=true

FROM openjdk:22-jdk-slim 

WORKDIR /app

COPY --from=build /app/target/newsdata_io_accessor.jar .

EXPOSE 7003

CMD ["java", "-jar", "newsdata_io_accessor.jar"]

# FROM openjdk:22-jdk-slim
# COPY newsdata_io_accessor/target/newsdata_io_accessor.jar app.jar
# EXPOSE 7001
# ENTRYPOINT ["java","-jar","/app.jar"]