#Stage 1: Download the dependecies
FROM maven:3.9.12-eclipse-temurin-25-alpine AS dependencies

WORKDIR /build
COPY pom.xml .

RUN mvn dependency:go-offline

# Stage 2: Build the application
FROM dependencies as builder
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 3: Run the application
FROM eclipse-temurin:25-jre-alpine as runtime
WORKDIR /app
COPY --from=builder /build/target/*.jar app.jar

EXPOSE 9012

ENTRYPOINT ["java", "-jar", "app.jar"]
