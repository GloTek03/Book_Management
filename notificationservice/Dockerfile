# ---------- Stage 1: Build ----------
FROM maven:3.9.6-eclipse-temurin-17 as build
WORKDIR /app
COPY ./notificationservice ./notificationservice
COPY ./commonservice ./commonservice

WORKDIR /app/commonservice
RUN mvn clean install -DskipTests

WORKDIR /app/notificationservice
RUN mvn clean install -DskipTests

# ---------- Stage 2: Run ----------
FROM maven:3.9.6-eclipse-temurin-17
WORKDIR /app
COPY --from=build /app/notificationservice/target/*.jar notificationservice.jar
EXPOSE 9003
ENTRYPOINT ["java", "-jar", "notificationservice.jar"]