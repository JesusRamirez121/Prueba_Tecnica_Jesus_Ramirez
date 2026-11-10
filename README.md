# Prueba Técnica — Jesus Ramirez

Repositorio con tres ejercicios de automatización de pruebas. Cada carpeta es un proyecto independiente con su propio readme.

| Carpeta | Tipo de prueba | Herramienta | Readme |
|---|---|---|---|
| `ejercicio-API/` | API REST (CRUD de usuarios en PetStore) | Karate DSL 1.4.0 + JUnit 5 + Gradle | [readme.md](ejercicio-API/readme.md) |
| `ejercicio-e2e/` | E2E: compra como invitado en OpenCart | Serenity BDD 4.2.1 + Cucumber + Selenium (Java 21) | [README.md](ejercicio-e2e/README.md) |
| `ejercicio-performance/` | Carga y rendimiento | Apache JMeter 5.6.3 (`Ejercicio1`) y k6 (`Ejercicio2`) | [readme.md](ejercicio-performance/readme.md) |

## Requisitos generales

- Java 11 (API), Java 21 (E2E) o Java 11+ (JMeter). Gradle se usa siempre mediante el wrapper incluido.
- Conexión a internet: las pruebas consumen APIs y sitios públicos.
- En **Windows**, clonar con soporte de rutas largas (algunas librerías vendidas superan 260 caracteres):

```bash
git config --global core.longpaths true
```

## Ejecución rápida

```bash
# API
cd ejercicio-API
./gradlew clean test          # Windows: gradlew.bat clean test

# E2E (requiere Google Chrome)
cd ejercicio-e2e
./gradlew clean test          # Windows: gradlew.bat clean test

# Performance: ver ejercicio-performance/readme.md (se ejecuta desde su carpeta Ejercicio1)
```

Los reportes generados (`build/`, `target/`) no se versionan.
