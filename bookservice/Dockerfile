# ---------- Stage 1: Build ----------
FROM maven:3.9.6-eclipse-temurin-17 as build
WORKDIR /app
COPY ./bookservice ./bookservice
COPY ./commonservice ./commonservice

WORKDIR /app/commonservice
RUN mvn clean install -DskipTests

WORKDIR /app/bookservice
RUN mvn clean install -DskipTests

# ---------- Stage 2: Run ----------
FROM maven:3.9.6-eclipse-temurin-17
WORKDIR /app
COPY --from=build /app/bookservice/target/*.jar bookservice.jar
EXPOSE 9001
ENTRYPOINT ["java", "-jar", "bookservice.jar"]