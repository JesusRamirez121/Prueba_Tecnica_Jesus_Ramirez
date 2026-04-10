# 🚀 Prueba Técnica - Automatización API REST con Karate

## 🐾 PetStore Swagger - Gestión de Usuarios

![Karate](https://img.shields.io/badge/Framework-Karate_DSL-blueviolet)
![Gradle](https://img.shields.io/badge/Build_Tool-Gradle-green)
![Java](https://img.shields.io/badge/Language-Java-orange)

---

### 📝 Descripción

Este proyecto contiene pruebas automatizadas para los endpoints de **gestión de usuarios** de la API de PetStore, utilizando **Karate DSL** para orquestar las llamadas y validaciones.

---

### ✅ Casos de Prueba Implementados

| ID     | Descripción                      | Endpoint              | Método |
| :----- | :------------------------------- | :-------------------- | :----: |
| TC-001 | Crear un usuario                 | `/user`               | `POST` |
| TC-002 | Buscar el usuario creado         | `/user/{username}`    | `GET`  |
| TC-003 | Actualizar nombre y correo       | `/user/{username}`    | `PUT`  |
| TC-004 | Buscar el usuario actualizado    | `/user/{username}`    | `GET`  |
| TC-005 | Eliminar el usuario              | `/user/{username}`    | `DELETE`|
| TC-006 | Verificar eliminación (404)      | `/user/{username}`    | `GET`  |

---

### 🏗️ Estructura del Proyecto

```
ejercicio-API/
├── build.gradle
├── settings.gradle
├── gradlew / gradlew.bat
├── readme.md
├── conclusiones.txt
└── src/test/
    ├── java/com/co/jesusr/
    │   ├── UserRunner.java
    │   └── user.feature
    └── resources/
        └── karate-config.js
```

---

### 🛠️ Requisitos Previos

- **Java JDK 11** o superior.
- **Gradle 8.x** (o usar el Wrapper incluido).
- Conexión a internet.

---

### 🚀 Instrucciones de Ejecución

1.  **Abrir una terminal** en la raíz del proyecto.

2.  **Ejecutar los tests** con el Gradle Wrapper:

    ```bash
    # En Windows
    ./gradlew.bat clean test

    # En Linux/Mac
    ./gradlew clean test
    ```

3.  **Verificar los resultados** en la consola.

---

### 📊 Reportes Generados

Al finalizar la ejecución, el reporte HTML interactivo estará disponible en:

-   `build/karate-reports/karate-summary.html`

---

### 🧪 Datos de Prueba

Los datos de prueba se gestionan en `src/test/resources/karate-config.js`.

---
**Autor:** Jesus Ramirez
