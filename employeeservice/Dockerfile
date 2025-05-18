# ---------- Stage 1: Build ----------
FROM maven:3.9.6-eclipse-temurin-17 as build
WORKDIR /app
COPY ./employeeservice ./employeeservice
COPY ./commonservice ./commonservice

WORKDIR /app/commonservice
RUN mvn clean install -DskipTests

WORKDIR /app/employeeservice
RUN mvn clean install -DskipTests

# ---------- Stage 2: Run ----------
FROM maven:3.9.6-eclipse-temurin-17
WORKDIR /app
COPY --from=build /app/employeeservice/target/*.jar employeeservice.jar
EXPOSE 9002
ENTRYPOINT ["java", "-jar", "employeeservice.jar"]