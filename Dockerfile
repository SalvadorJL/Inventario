FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/inventario-0.0.1-SNAPSHOT.jar inventario.jar
EXPOSE 7080
ENTRYPOINT ["java", "-jar", "inventario.jar"]