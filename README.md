# Inventario
Prueba tecnica

para ejecutar el jar es 
java -jar inventario-0.0.1-SNAPSHOT.jar

para ver la documentacion en swagger se accedea este link
http://localhost:7080/swagger-ui/index.html

para construir el contenedor (no fucionara, hay un error con el tipo enum al crearse la tabla en el docker)

1. se debe cambiar los datos de la base de datos en el .env
 
SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/inventario
SPRING_DATASOURCE_USERNAME=usuario
SPRING_DATASOURCE_PASSWORD=contrasenia
POSTGRES_DB=inventario
POSTGRES_USER=usuario
POSTGRES_PASSWORD=contrasenia

2.
docker-compose up --build

levantara 2 imagenes la de base de datos y la aplicacion
