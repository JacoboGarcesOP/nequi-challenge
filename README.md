# Proyecto Base Implementando Clean Architecture

Este proyecto sigue el plugin de Clean Architecture para Gradle y utiliza Java 21, Spring Boot 3 (reactivo), R2DBC con PostgreSQL y SLF4J para logs.

## Requisitos

- Java 21 (JDK)
- Gradle Wrapper (incluido: `./gradlew` / `gradlew.bat`)
- Docker y Docker Compose (opcional, para ejecución en contenedor)

## Clonar el repositorio

```bash
git clone <URL_REPO>
cd nequi-challenge
```

Reemplaza `<URL_REPO>` por la URL de tu fork o del repositorio oficial.

## Variables de entorno necesarias

La aplicación se configura vía variables de entorno (ver `applications/app-service/src/main/resources/application.yaml`).

- `SERVER_PORT`: puerto HTTP del servicio (ej. `8080`).
- `DATABASE_HOST`: host de PostgreSQL (ej. `localhost`).
- `DATABASE_NAME`: base de datos (ej. `nequi`).
- `DATABASE_SCHEMA`: esquema (ej. `public`).
- `DATABASE_USER`: usuario DB.
- `DATABASE_PASSWORD`: contraseña DB.
- `ALLOWED_ORIGINS`: orígenes CORS permitidos (ej. `*` o lista separada por comas).
- `ALLOWED_METHODS`: métodos HTTP permitidos (ej. `GET,POST,PUT,DELETE`).
- `BASE_URL`: prefijo base de la API (ej. `/v1/api`).

Ejemplo de `.env` local:

```bash
SERVER_PORT=8080
DATABASE_HOST=localhost
DATABASE_NAME=nequi
DATABASE_SCHEMA=public
DATABASE_USER=postgres
DATABASE_PASSWORD=postgres
ALLOWED_ORIGINS=*
ALLOWED_METHODS=GET,POST,PUT,DELETE
BASE_URL=/v1/api
```

## Ejecutar local con Gradle

1) Exporta variables (o crea un `.env` y cárgalo):

```bash
export $(grep -v '^#' .env | xargs)   # macOS/Linux
# En Windows PowerShell: setx SERVER_PORT 8080  (repite para cada variable)
```

2) Levanta el servicio:

```bash
./gradlew :applications:app-service:bootRun          # macOS/Linux
gradlew.bat :applications:app-service:bootRun        # Windows
```

El servicio quedará expuesto en `http://localhost:${SERVER_PORT}`.

## Ejecutar con Docker

Construye la imagen y ejecuta el contenedor (el `Dockerfile` ya realiza el build del módulo `app-service`):

```bash
docker build -t nequi-challenge .
docker run --rm -p 8080:8080 \
  -e SERVER_PORT=8080 \
  -e DATABASE_HOST=host.docker.internal \
  -e DATABASE_NAME=nequi \
  -e DATABASE_SCHEMA=public \
  -e DATABASE_USER=postgres \
  -e DATABASE_PASSWORD=postgres \
  -e ALLOWED_ORIGINS=* \
  -e ALLOWED_METHODS=GET,POST,PUT,DELETE \
  -e BASE_URL=/v1/api \
  nequi-challenge
```

Ajusta las variables según tu entorno y puertos. Nota: el `application.yaml` usa `sslMode=require` en la URL R2DBC; asegúrate de que tu base de datos permita conexión con SSL o ajusta el parámetro si tu entorno local no lo requiere.

## Endpoints y documentación

- Base de la API: `http://localhost:${SERVER_PORT}/v1/api`
- Rutas gestionadas vía `RouterFunction` en `infrastructure/entry-points/reactive-web`.
- OpenAPI/Swagger: revisa la configuración en `applications/app-service/src/main/java/co/com/bancolombia/config/OpenApiConfig.java`. Si tienes Swagger UI habilitado, suele estar en `/swagger-ui.html` o `/swagger-ui/index.html`.

## Estructura del proyecto

Monorepo multi-módulo (Gradle):

- `domain/model`: entidades del dominio, value objects y gateways (interfaces).
- `domain/usecase`: casos de uso; orquestan reglas de negocio y dependen de gateways.
- `infrastructure/entry-points/reactive-web`: capa de entrada reactiva (handlers y router).
- `infrastructure/driven-adapters/r2dbc-postgresql`: implementación R2DBC sobre PostgreSQL (repositories/adapters).
- `applications/app-service`: módulo ejecutable (Spring Boot) que ensambla y arranca la app.

### Flujo (alto nivel)

1. Request HTTP entra por `reactive-web` (`RouterRest` -> `Handler`).
2. `Handler` valida y delega en casos de uso (`usecase`).
3. Casos de uso llaman a gateways definidos en `model`.
4. Adapters R2DBC implementan los gateways contra PostgreSQL.
5. `app-service` configura beans, OpenAPI y arranque.

## Persistencia (R2DBC + PostgreSQL)

- Repositorios reactivos en `infrastructure/driven-adapters/r2dbc-postgresql/src/main/java/.../repository`.
- Adapters que transforman `Entity` <-> `Domain` en `.../adapter`.
- La conexión se define en `application.yaml` usando variables de entorno descritas arriba.

## Logs

- SLF4J con Log4j2 (`applications/app-service/src/main/resources/log4j2.properties`).
- Handlers y adapters incluyen logs de entrada y de error para trazabilidad.

## Comandos útiles

```bash
# Ejecutar tests
./gradlew test

# Compilar JAR de la app
./gradlew :applications:app-service:bootJar

# Formato/estáticos (si aplica en tu entorno)
./gradlew check
```

## Referencias

- Lee el artículo [Clean Architecture — Aislando los detalles](https://medium.com/bancolombia-tech/clean-architecture-aislando-los-detalles-4f9530f35d7a)

