================================================================
EJERCICIO E2E - FLUJO DE COMPRA (OpenCart)
Instrucciones de ejecucion paso a paso
================================================================

DESCRIPCION:
Prueba End-to-End automatizada del flujo completo de compra como
usuario invitado en OpenCart (http://opencart.abstracta.us/).
Escenario: buscar y agregar dos productos (MacBook e iPhone),
completar el checkout como invitado y verificar la confirmacion
del pedido.

----------------------------------------------------------------
VERSIONES DE TECNOLOGIAS
----------------------------------------------------------------
- Java                  : 21 (JDK 21 LTS)
- Gradle                : 8.x (incluido via Gradle Wrapper)
- Serenity BDD          : 4.2.1
- Selenium WebDriver    : Gestionado por WebDriverManager 5.7.0
- Cucumber              : 7.16.1
- JUnit                 : 5.11.0 (Jupiter)
- Google Chrome         : Ultima version estable disponible
- ChromeDriver          : Descargado automaticamente por WebDriverManager
- Lombok                : 1.18.34
- AssertJ               : 3.24.2
- Logback               : 1.4.x
- Sistema Operativo     : Windows 10/11

----------------------------------------------------------------
PRE-REQUISITOS
----------------------------------------------------------------
1. Tener instalado Java 21 (JDK).
   Verificar con:
     java -version
   Debe mostrar: openjdk 21 o similar.
   Descargar en: https://adoptium.net/es/temurin/releases/

2. Tener instalado Google Chrome (version actual).
   WebDriverManager descarga automaticamente el ChromeDriver
   compatible, no es necesario instalarlo manualmente.

3. Conexion a internet activa:
   - Para descargar ChromeDriver automaticamente (primera ejecucion).
   - Para acceder a http://opencart.abstracta.us/

4. NO es necesario instalar Gradle globalmente.
   El proyecto incluye el Gradle Wrapper (gradlew.bat).

5. Variables de entorno recomendadas:
   JAVA_HOME = C:\Program Files\Eclipse Adoptium\jdk-21.x.x.x
   PATH += %JAVA_HOME%\bin

----------------------------------------------------------------
ESTRUCTURA DEL PROYECTO
----------------------------------------------------------------
ejercicio-e2e/
  src/
    main/java/com/co/jesusr/
      ui/          -> Page Objects (localizadores de elementos)
      task/        -> Tareas Screenplay (acciones de negocio)
      questions/   -> Preguntas Screenplay (aserciones)
    test/java/com/co/jesusr/
      runners/     -> TestRunner.java (configuracion JUnit+Cucumber)
      steps/       -> CompraSteps.java (definicion de pasos Gherkin)
    test/resources/
      features/    -> comprar.feature (escenario BDD en Gherkin)
      serenity.conf -> Configuracion de browser, URL y reportes
  build.gradle     -> Dependencias y configuracion de build
  gradlew.bat      -> Wrapper de Gradle para Windows

----------------------------------------------------------------
INSTRUCCIONES DE EJECUCION
----------------------------------------------------------------

OPCION A - Ejecucion completa (recomendada):
--------------------------------------------
1. Abrir una terminal (CMD o PowerShell).

2. Navegar al directorio del proyecto:
     cd UnidadDondeSeClono\DirDondeSeClono\ejercicio-e2e

3. Ejecutar el siguiente comando:
     .\gradlew.bat clean test aggregate

   Que hace cada tarea:
     clean     -> Limpia el directorio build/ de ejecuciones previas
     test      -> Compila y ejecuta los tests con JUnit + Cucumber
     aggregate -> Genera el reporte HTML de Serenity BDD

4. Durante la ejecucion:
   - Se abrira una ventana de Google Chrome automaticamente.
   - El browser navegara por OpenCart ejecutando el flujo de compra.
   - NO interactuar con el browser durante la ejecucion.
   - El test tarda aproximadamente 2-3 minutos en completarse.

5. Al finalizar, los resultados se muestran en consola:
   - BUILD SUCCESSFUL -> todos los tests pasaron
   - BUILD FAILED     -> al menos un test fallo

OPCION B - Solo ejecutar tests (sin reporte HTML):
--------------------------------------------------
     .\gradlew.bat clean test

OPCION C - Solo generar reporte (si ya se ejecutaron los tests):
----------------------------------------------------------------
     .\gradlew.bat aggregate

OPCION D - Ejecucion con entorno especifico:
--------------------------------------------
     .\gradlew.bat clean test aggregate -Denvironment=default

----------------------------------------------------------------
VER LOS RESULTADOS
----------------------------------------------------------------
Reporte HTML interactivo (con capturas de pantalla):
  Abrir en el navegador:
  target\site\serenity\index.html

Reporte JSON (para integracion CI/CD):
  target\comprar.json

Reporte XML (JUnit):
  target\comprar.xml

Logs de ejecucion:
  Se muestran en consola durante la ejecucion.
  Formato: HH:mm:ss.SSS [thread] LEVEL logger - mensaje

----------------------------------------------------------------
ESCENARIO DE PRUEBA
----------------------------------------------------------------
Archivo: src/test/resources/features/comprar.feature

Titulo: "Compra exitosa de dos productos como usuario invitado"

Pasos del flujo:
  1.  Navegar a http://opencart.abstracta.us/
  2.  Buscar "MacBook" y agregarlo al carrito
  3.  Buscar "iPhone" y agregarlo al carrito
  4.  Ver el carrito de compras
  5.  Verificar que el carrito contiene 2 productos
  6.  Proceder al checkout
  7.  Manejar advertencia SSL si aparece (IE/Edge)
  8.  Seleccionar Guest Checkout
  9.  Completar formulario de facturacion:
        Nombre:      Juan
        Apellido:    Perez
        Email:       juan.perez.test@mailtest.com
        Telefono:    3001234567
        Direccion:   Calle 123 # 45-67
        CiudaUnidadDondeSeClono      Bogota
        Codigo ZIP:  110111
        Pais:        Colombia
  10. Continuar con la entrega
  11. Seleccionar metodo de envio
  12. Aceptar terminos y condiciones
  13. Confirmar el pedido
  14. Verificar mensaje "Your order has been placed!"

Criterio de exito:
  - Carrito contiene 2 productos (verificacion intermedia)
  - Mensaje de confirmacion visible al finalizar

----------------------------------------------------------------
CONFIGURACION (serenity.conf)
----------------------------------------------------------------
Driver    : Chrome
Headless  : NO (browser visible)
URL base  : http://opencart.abstracta.us/
Capturas  : Despues de cada paso

Para ejecutar en modo headless (sin interfaz grafica):
  Editar src/test/resources/serenity.conf y cambiar:
    headless = false  -->  headless = true

----------------------------------------------------------------
NOTAS IMPORTANTES
----------------------------------------------------------------
- La primera ejecucion puede tardar mas tiempo porque
  WebDriverManager descarga el ChromeDriver desde internet.

- El sitio opencart.abstracta.us puede mostrar advertencias
  de certificado SSL. La prueba maneja esto automaticamente.

- Si el test falla por timeout, puede ser que el servidor
  de OpenCart este lento. Intentar nuevamente.

- Para limpiar completamente entre ejecuciones:
    .\gradlew.bat clean
  Esto elimina build/ y todos los artefactos generados.

- El directorio target/ contiene los reportes JSON/XML y
  NO se limpia con el comando clean de Gradle (solo se
  sobreescribe en cada ejecucion).
================================================================
