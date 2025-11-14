# Etapa de construcción
FROM gradle:8.5-jdk21 AS build

WORKDIR /app
COPY build.gradle settings.gradle ./
COPY src ./src

RUN gradle clean build -x test

# Etapa final (sin Alpine para evitar problemas)
FROM eclipse-temurin:21-jdk

WORKDIR /app

# Copiamos el JAR desde la fase de build
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
