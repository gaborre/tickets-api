#!/bin/bash

echo "Limpiando y construyendo artefactos de Maven..."
mvn clean
mvn install
echo "Intentando borrar la imagen tickets-api (ignorando error si no existe)..."
sudo docker rmi -f tickets-api || true
echo "Construyendo la nueva imagen de Docker..."
sudo docker build -t tickets-api .
echo "Ejecutando el contenedor..."
sudo docker run -p 8080:8080 tickets-api
echo "Contenedor en ejecución en el puerto 8080."
echo "Para detener el contenedor, use 'sudo docker ps' para encontrar el ID del contenedor y luego 'sudo docker stop <container_id>'."
echo "Para ver los logs del contenedor, use 'sudo docker logs -f <container_id>'."
echo "Para acceder al contenedor, use 'sudo docker exec -it <container_id> /bin/bash'."
echo "Para eliminar la imagen, use 'sudo docker rmi -f tickets-api'."
echo "Proceso completado."
