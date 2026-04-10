================================================================
  Prueba Técnica - Automatización API REST
  PetStore Swagger - Gestión de Usuarios
  Framework: Karate DSL + JUnit 5 + Gradle
  Autor: Jesus Ramirez
================================================================

DESCRIPCION
-----------
Proyecto de pruebas automatizadas para los endpoints de usuario
de la API PetStore (https://petstore.swagger.io/v2).

Casos de prueba implementados:
  TC-001 → Crear un usuario                  POST /user
  TC-002 → Buscar el usuario creado          GET  /user/{username}
  TC-003 → Actualizar nombre y correo        PUT  /user/{username}
  TC-004 → Buscar el usuario actualizado     GET  /user/{username}
  TC-005 → Eliminar el usuario               DELETE /user/{username}
           (implementado con Karate + Patrón Screenplay)
  TC-006 → Verificar eliminación (404)       GET  /user/{username}

PATRON DE DISEÑO: SCREENPLAY en Karate
---------------------------------------
El patrón Screenplay organiza las pruebas en tres capas:

  ACTOR     → El tester de API (contexto de prueba / karate-config.js)
  TAREAS    → Feature files en tasks/ (@ignore, reutilizables)
              - tasks/crear-usuario.feature
              - tasks/buscar-usuario.feature
              - tasks/actualizar-usuario.feature
              - tasks/eliminar-usuario.feature
  PREGUNTAS → Assertions "And match response..." en cada escenario

El TC-005 demuestra explícitamente el patrón:
  El Actor NO llama directamente al HTTP DELETE.
  En cambio, delega en la Tarea "EliminarUsuario":
    * def resultado = call read('tasks/eliminar-usuario.feature')
  La Tarea encapsula el endpoint, método y validación de bajo nivel.

ESTRUCTURA DEL PROYECTO
-----------------------
ejercicio-API/
├── build.gradle                         -> Config Gradle + deps
├── settings.gradle                      -> Nombre del proyecto
├── gradlew / gradlew.bat                -> Gradle Wrapper
├── readme.txt                           -> Este archivo
├── conclusiones.txt                     -> Hallazgos y conclusiones
└── src/test/
    ├── java/com/co/jesusr/
    │   ├── UserRunner.java              -> Runner JUnit 5 + Karate
    │   ├── user.feature                 -> 6 escenarios de prueba
    │   └── tasks/                       -> Tareas Screenplay
    │       ├── crear-usuario.feature    -> Tarea: POST /user
    │       ├── buscar-usuario.feature   -> Tarea: GET /user/{u}
    │       ├── actualizar-usuario.feature -> Tarea: PUT /user/{u}
    │       └── eliminar-usuario.feature -> Tarea: DELETE /user/{u}
    └── resources/
        └── karate-config.js             -> Config global (baseUrl, datos)

REQUISITOS PREVIOS
------------------
- Java JDK 11 o superior
    Verificar: java -version
    Descargar: https://adoptium.net/

- Gradle 8.x (o usar el Gradle Wrapper incluido)
    Verificar: gradlew.bat --version

- Conexión a internet
    La API PetStore está en: https://petstore.swagger.io/v2

INSTRUCCIONES DE EJECUCION
---------------------------

OPCION A: Con Gradle Wrapper (recomendada)
------------------------------------------
1. Abrir una terminal (cmd o PowerShell) en la raíz del proyecto:
   cd D:\Prueba_Tecnica_Jesus_Ramirez\ejercicio-API

2. Ejecutar los tests:

   En Windows:
   gradlew.bat clean test

   En Linux/Mac:
   ./gradlew clean test

3. Esperar la ejecución (~30-60 segundos según conexión).

4. Verificar en consola que los 6 TCs pasaron:
   TC-001 PASSED | Usuario creado
   TC-002 PASSED | Usuario encontrado
   TC-003 PASSED | Usuario actualizado
   TC-004 PASSED | Datos actualizados confirmados
   TC-005 PASSED | Actor ejecutó Tarea EliminarUsuario
   TC-006 PASSED | Confirmado: usuario ya no existe

OPCION B: Con Gradle instalado globalmente
------------------------------------------
1. gradle clean test

OPCION C: Desde IntelliJ IDEA
------------------------------
1. Importar como proyecto Gradle (File > Open > seleccionar carpeta)
2. Esperar sincronización de dependencias
3. Click derecho en UserRunner.java > Run 'UserRunner'
   O click derecho en user.feature > Run feature

REPORTES GENERADOS
------------------
Después de la ejecución se generan los siguientes reportes:

1. Reporte Karate - Resumen general (abre en navegador):
   build/karate-reports/karate-summary.html
   → Muestra todos los TCs con estado PASSED/FAILED

2. Reporte Karate - Detalle por feature (request/response completo):
   build/karate-reports/com.co.jesusr.user.html
   → Muestra cada paso con el JSON enviado y recibido

3. Reporte Karate - Timeline de ejecución:
   build/karate-reports/karate-timeline.html

4. Reporte JUnit HTML (Gradle):
   build/reports/tests/test/index.html

5. Reporte JUnit XML (para CI/CD):
   build/test-results/test/TEST-*.xml

DATOS DE PRUEBA
---------------
Los datos están centralizados en karate-config.js:
  username:          jesusr_karate_test
  userId:            9100001
  firstName:         Jesus
  lastName:          Ramirez
  email:             jesus.ramirez@karate.test
  updatedFirstName:  Jesus Carlos
  updatedLastName:   Ramirez Gomez
  updatedEmail:      jesus.carlos@karate.updated

NOTAS IMPORTANTES
-----------------
- PetStore es una API pública de demostración; puede ser lenta o inestable.
- Los tests corren en orden secuencial (TC-001 → TC-006).
- Si TC-001 falla por username ya existente, el test TC-006 (404) lo limpiará.
- El username 'jesusr_karate_test' queda libre al finalizar la suite.
- No se necesita autenticación para la API PetStore.
