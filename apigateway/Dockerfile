# ---------- Stage 1: Build ----------
FROM maven:3.9.6-eclipse-temurin-17 as build
WORKDIR /app
COPY ./apigateway ./apigateway

WORKDIR /app/apigateway
RUN mvn clean install -DskipTests

# ---------- Stage 2: Run ----------
FROM maven:3.9.6-eclipse-temurin-17
WORKDIR /app
COPY --from=build /app/apigateway/target/*.jar apigateway.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "apigateway.jar"]