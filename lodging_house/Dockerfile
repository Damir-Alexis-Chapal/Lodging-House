
FROM gradle:8.5-jdk21 AS build

# Copiamos el código al contenedor
WORKDIR /app_lodging_house
COPY . .

# Compilamos el proyecto sin tests
RUN gradle clean build -x test

# Imagen final
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app_lodging_house

# Copiamos el JAR compilado desde la imagen anterior
COPY --from=build /app_lodging_house/build/libs/*.jar app.jar

# Puerto donde escucha Spring Boot
EXPOSE 8080

# Comando para ejecutar la app
ENTRYPOINT ["java", "-jar", "app.jar"]
