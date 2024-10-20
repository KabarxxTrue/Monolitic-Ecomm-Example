FROM maven:3.9.4-eclipse-temurin-17 AS build

WORKDIR /app

COPY . ./

RUN mvn dependency:go-offline -B

RUN mvn clean package -DskipTests

FROM maven:3.9.4-eclipse-temurin-17

WORKDIR /app

COPY --from=build /app/target/store_example-0.0.1-SNAPSHOT.jar /app/application.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/application.jar"]