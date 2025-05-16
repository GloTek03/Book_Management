FROM maven:3.9.6-eclipse-temurin-17 as build
WORKDIR /app
COPY ./eurekaservice ./eurekaservice
WORKDIR /app/eurekaservice
RUN mvn clean package -DskipTests

FROM maven:3.9.6-eclipse-temurin-17
WORKDIR /app
COPY --from=build /app/eurekaservice/target/*.jar eurekaservice.jar
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "eurekaservice.jar"]