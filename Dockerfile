# Etapa 1: Build
FROM gradle:8.14.3-jdk21-alpine AS builder

# Establecer directorio de trabajo
WORKDIR /home/gradle/src

# Copiar proyecto completo
COPY . .

# Construir solo el módulo app-service (sin tests)
RUN gradle :app-service:clean :app-service:bootJar -x test --no-daemon --stacktrace --info

# Etapa 2: Runtime
FROM eclipse-temurin:21-jre-alpine

# Crear usuario no-root para ejecutar la aplicación
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Establecer directorio de trabajo
WORKDIR /app

# Copiar el JAR desde la etapa de build
COPY --from=builder /home/gradle/src/applications/app-service/build/libs/*.jar app.jar

# Exponer puerto (ajusta según tu aplicación)
EXPOSE 8080

# Configurar opciones de JVM para contenedor
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0"

# Ejecutar la aplicación
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]