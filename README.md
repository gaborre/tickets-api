# Tickets API

API REST para la gestión de usuarios y tickets, construida con Spring Boot y protegida con JWT. Expone recursos para crear, consultar, actualizar y eliminar usuarios y tickets, además de filtrar tickets por usuario y estado.

## Stack y versiones clave
- Java 21
- Maven 3.9.8
- Spring Boot 3.5.8 (Web, Security, Data JPA, Cache)
- Springdoc OpenAPI 2.8.14 (Swagger UI)
- ModelMapper 3.2.4
- JSON Web Token (jjwt 0.13.0)
- Base de datos en memoria H2 2.x

## Requisitos previos
1. Docker instalado y funcionando.
2. Maven 3.9.8 disponible en el `PATH`.
3. JDK 21 instalado.
4. Puerto `8080` libre.
5. Permisos de ejecución sobre `build-and-run.sh` (`chmod +x build-and-run.sh`).

## Puesta en marcha local
1. Clona este repositorio y ubícate en su raíz.
2. Asegúrate de que `build-and-run.sh` tenga permisos de ejecución.
3. Ejecuta el script:
   ```bash
   ./build-and-run.sh
   ```
   El script compila con Maven, construye la imagen Docker y levanta el contenedor exponiendo la API en `http://localhost:8080`.
4. Accede a la documentación interactiva en `http://localhost:8080/swagger-ui.html`.

## Consola H2
Puedes revisar el estado de la base en memoria entrando a `http://localhost:8080/h2-console`. Configura:
- JDBC URL: `jdbc:h2:mem:demo`
- Usuario: `sa`
- Password: *(vacío)*

## Estados de los tickets
Los únicos valores aceptados para `status` son `OPENED` y `CLOSED`.

## Flujo de autenticación
1. Obtén un JWT **antes** de consumir cualquier endpoint protegido:
   ```bash
   curl -X POST http://localhost:8080/api/v1/admin/get-valid-jwt \
     -H 'Content-Type: application/json' \
     -d '{
       "subject": "tester",
       "audience": "tickets-clients"
     }'
   ```
2. Usa el token recibido en el encabezado `Authorization: Bearer <JWT>` para todas las demás llamadas.

> El endpoint `/api/v1/admin/get-valid-jwt` no requiere autenticación previa.

## Endpoints y ejemplos de uso
Todos los ejemplos usan `Authorization: Bearer <JWT>` y la URL base `http://localhost:8080`. También puedes ejecutar cada operación desde Swagger UI (`/swagger-ui.html`).

### Administración (JWT)
- **POST** `/api/v1/admin/get-valid-jwt` — genera un token válido. *(Ver sección anterior.)*

### Tickets (`/api/v1/tickets`)
- **POST** `/api/v1/tickets`
  ```bash
  curl -X POST http://localhost:8080/api/v1/tickets \
    -H 'Authorization: Bearer <JWT>' \
    -H 'Content-Type: application/json' \
    -d '{
      "description": "Restablecer contraseña",
      "userId": 1,
      "status": "OPENED"
    }'
  ```
- **GET** `/api/v1/tickets/{id}`
  ```bash
  curl -H 'Authorization: Bearer <JWT>' \
    http://localhost:8080/api/v1/tickets/1
  ```
- **GET** `/api/v1/tickets/uuid/{uuid}`
- **GET** `/api/v1/tickets?status=OPENED&page=1&limit=10`
  ```bash
  curl -H 'Authorization: Bearer <JWT>' \
    'http://localhost:8080/api/v1/tickets?status=OPENED&page=1&limit=10'
  ```
- **PUT** `/api/v1/tickets/{id}`
  ```bash
  curl -X PUT http://localhost:8080/api/v1/tickets/1 \
    -H 'Authorization: Bearer <JWT>' \
    -H 'Content-Type: application/json' \
    -d '{
      "description": "Restablecer contraseña (actualizado)",
      "status": "CLOSED"
    }'
  ```
- **DELETE** `/api/v1/tickets/{id}`
  ```bash
  curl -X DELETE \
    -H 'Authorization: Bearer <JWT>' \
    http://localhost:8080/api/v1/tickets/1
  ```

### Usuarios (`/api/v1/users`)
- **POST** `/api/v1/users`
  ```bash
  curl -X POST http://localhost:8080/api/v1/users \
    -H 'Authorization: Bearer <JWT>' \
    -H 'Content-Type: application/json' \
    -d '{
      "firstName": "Ada",
      "lastName": "Lovelace"
    }'
  ```
- **GET** `/api/v1/users/{id}`
- **GET** `/api/v1/users/uuid/{uuid}`
- **GET** `/api/v1/users?page=1&limit=10`
- **PUT** `/api/v1/users/{id}` (mismo payload que creación).

### Tickets por usuario (`/api/v1/users/{userId}/tickets`)
- **GET** `/api/v1/users/{userId}/tickets?status=OPENED&page=1&limit=5`
  ```bash
  curl -H 'Authorization: Bearer <JWT>' \
    'http://localhost:8080/api/v1/users/1/tickets?status=OPENED&page=1&limit=5'
  ```

## Notas adicionales
- Todos los controladores exponen las operaciones descritas en Swagger, desde donde puedes probarlas rápidamente.
- Usa `limit` entre 5 y 20 y `page` ≥ 1 al paginar (según las validaciones de `CriteriaDTO`).
- El script `build-and-run.sh` aplica `mvn clean package` y ejecuta la aplicación dentro de Docker para replicar el entorno productivo.
