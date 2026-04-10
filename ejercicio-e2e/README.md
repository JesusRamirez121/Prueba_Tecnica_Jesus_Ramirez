# Ejercicio E2E — Flujo de Compra en OpenCart

Prueba End-to-End automatizada del flujo completo de compra como **usuario invitado** en [OpenCart Demo](http://opencart.abstracta.us/), implementada con **Serenity BDD** y el **patrón Screenplay**.

---

## Stack tecnológico

| Tecnología | Versión | Rol |
|---|---|---|
| Java | 21 (JDK LTS) | Lenguaje |
| Gradle | 8.x (Wrapper incluido) | Build tool |
| Serenity BDD | 4.2.1 | Framework E2E + reportes |
| Cucumber | 7.16.1 | BDD / Gherkin |
| JUnit | 5.11.0 (Jupiter) | Motor de tests |
| Selenium WebDriver | Gestionado por WDM | Automatización browser |
| WebDriverManager | 5.7.0 | Gestión automática de drivers |
| Google Chrome | Última versión estable | Browser de ejecución |
| Lombok | 1.18.34 | Reducción de boilerplate |
| AssertJ | 3.24.2 | Aserciones fluidas |

---

## Arquitectura — Patrón Screenplay

```
┌─────────────────────────────────────────────────────────────┐
│                    CAPA DE ESPECIFICACIÓN                   │
│   comprar.feature   (Gherkin / Cucumber — lenguaje español) │
└─────────────────────────────────┬───────────────────────────┘
                                  │
┌─────────────────────────────────▼───────────────────────────┐
│                      CAPA DE PASOS                          │
│   CompraSteps.java   (@Dado / @Cuando / @Entonces)          │
└──────────┬─────────────────────────┬────────────────────────┘
           │                         │
┌──────────▼──────────┐   ┌──────────▼──────────────────────┐
│   CAPA DE TAREAS    │   │      CAPA DE PREGUNTAS           │
│  task/*.java        │   │   questions/*.java               │
│  Acciones de negocio│   │  Aserciones sobre el estado      │
└──────────┬──────────┘   └──────────┬────────────────────────┘
           │                         │
┌──────────▼─────────────────────────▼────────────────────────┐
│                     CAPA DE UI (Page Objects)               │
│   ui/*.java  — Localizadores XPath/CSS por pantalla         │
└─────────────────────────────────────────────────────────────┘
```

### Componentes principales

| Carpeta | Clase | Responsabilidad |
|---|---|---|
| `ui/` | `PaginaPrincipalUI` | Localizadores de la home y buscador |
| `ui/` | `PaginaProductoUI` | Botón "Add to Cart" y mensajes |
| `ui/` | `PaginaCarritoUI` | Items del carrito y botón Checkout |
| `ui/` | `PaginaCheckoutUI` | Formulario de facturación y opciones |
| `ui/` | `PaginaConfirmacionUI` | Mensaje de confirmación final |
| `ui/` | `ErrorCertificadoDigitalIExplorerUi` | Advertencia SSL |
| `task/` | `BuscarYAgregarProductoTask` | Busca un producto y lo agrega al carrito |
| `task/` | `VerCarritoDeComprasTask` | Navega al carrito |
| `task/` | `ProcederAlCheckoutTask` | Inicia el proceso de pago |
| `task/` | `SitioNoSeguroTask` | Maneja advertencia de certificado SSL |
| `task/` | `SeleccionarGuestCheckoutTask` | Elige modo invitado |
| `task/` | `CompletarFormularioFacturacionTask` | Rellena datos de facturación |
| `task/` | `ContinuarConEntregaTask` | Pasa al paso de entrega |
| `task/` | `SeleccionarMetodoEnvioTask` | Elige el envío |
| `task/` | `SeleccionarPagoYAceptarTerminosTask` | Pago y términos |
| `task/` | `ConfirmarPedidoTask` | Confirma el pedido |
| `questions/` | `CarritoContieneProductosQuestion` | ¿Cuántos items hay en el carrito? |
| `questions/` | `MensajeDeConfirmacionQuestion` | ¿Qué dice el mensaje de confirmación? |

---

## Escenario de prueba

```gherkin
# src/test/resources/features/comprar.feature

Característica: Compra en OpenCart
  Como usuario invitado
  Quiero comprar dos productos en OpenCart
  Para validar el flujo completo de compra

  Escenario: Compra exitosa de dos productos como usuario invitado
    Dado  que el usuario navega a la pagina principal
    Cuando busca y agrega el producto "MacBook" al carrito
    Y     busca y agrega el producto "iPhone" al carrito
    Y     ve el carrito de compras
    Entonces el carrito debe contener al menos 2 productos
    Cuando  procede al checkout
    Y       selecciona guest checkout
    Y       completa el formulario de facturacion
    Y       continua con la entrega
    Y       selecciona el metodo de envio
    Y       selecciona el metodo de pago y acepta terminos
    Y       confirma el pedido
    Entonces debe ver el mensaje "Your order has been placed!"
```

---

## Prerrequisitos

- ✅ **Java 21** instalado y `JAVA_HOME` configurado
- ✅ **Google Chrome** (versión actual)
- ✅ **Conexión a internet** (primera ejecución descarga ChromeDriver)
- ✅ El Gradle Wrapper (`gradlew.bat`) está incluido — **no requiere Gradle global**

Verificar Java:
```cmd
java -version
# openjdk 21... ✓
```

---

## Ejecución

### Ejecución completa (recomendada)
```cmd
cd UnidadDondeSeClono\DirectorioDondeSeClono\ejercicio-e2e
.\gradlew.bat clean test aggregate
```

| Tarea | Descripción |
|---|---|
| `clean` | Limpia artefactos de ejecuciones previas |
| `test` | Compila y ejecuta los tests |
| `aggregate` | Genera el reporte HTML interactivo |

### Solo tests (sin reporte)
```cmd
.\gradlew.bat clean test
```

### Solo reporte (si ya se ejecutaron los tests)
```cmd
.\gradlew.bat aggregate
```

> ⏱ **Duración aproximada:** 2–3 minutos por ejecución completa.  
> 🖥 Se abrirá Chrome automáticamente — no interactuar con el browser.

---

## Ver resultados

| Formato | Ruta |
|---|---|
| **HTML interactivo** (con screenshots) | `target/site/serenity/index.html` |
| **JSON** (CI/CD) | `target/comprar.json` |
| **XML JUnit** | `target/comprar.xml` |

```cmd
# Abrir reporte HTML directamente
start target\site\serenity\index.html
```

---

## Configuración (`serenity.conf`)

```hocon
webdriver {
  driver = chrome
  autodownload = true
  capabilities {
    "goog:chromeOptions" {
      args = ["--start-maximized", "--disable-notifications",
              "--no-sandbox", "--disable-dev-shm-usage"]
    }
  }
}

serenity {
  project.name = "Ejercicio E2E - Flujo de Compra"
  take.screenshots = AFTER_EACH_STEP
}

environments {
  default {
    webdriver.base.url = "http://opencart.abstracta.us/"
  }
}
```

**Modo headless** (para CI/CD sin pantalla):
```hocon
# Agregar en capabilities:
"goog:chromeOptions" {
  args = [..., "--headless=new"]
}
```

---

## Estructura del proyecto

```
ejercicio-e2e/
├── src/
│   ├── main/java/com/co/jesusr/
│   │   ├── ui/          # Localizadores de elementos (6 clases)
│   │   ├── task/        # Tareas Screenplay (10 clases)
│   │   └── questions/   # Preguntas / aserciones (2 clases)
│   └── test/
│       ├── java/com/co/jesusr/
│       │   ├── runners/TestRunner.java
│       │   └── steps/CompraSteps.java
│       └── resources/
│           ├── features/comprar.feature
│           ├── serenity.conf
│           └── logback-test.xml
├── build.gradle
├── settings.gradle
├── gradlew.bat
├── readme.txt         ← instrucciones detalladas de ejecución
├── conclusiones.txt   ← hallazgos y análisis del ejercicio
└── README.md          ← este archivo
```

---

## Hallazgos principales

### ✅ Fortalezas implementadas
- **Manejo automático de SSL:** `SitioNoSeguroTask` detecta y bypasea advertencias de certificado sin intervención manual.
- **Esperas inteligentes:** `WaitUntil` con timeout de 10s en todos los elementos críticos; no hay `Thread.sleep()` fijos.
- **AJAX dinámico:** El campo Region/State espera la carga asíncrona tras seleccionar país.
- **Pasos opcionales:** Try-catch para elementos que pueden o no aparecer según el estado del carrito.
- **Driver autogestionado:** WebDriverManager elimina la configuración manual de ChromeDriver.

### 🔧 Áreas de mejora identificadas
- Agregar escenarios negativos (campos inválidos, carrito vacío, producto sin stock).
- Externalizar datos de prueba a tablas de ejemplos (`Scenario Outline`) o archivos JSON.
- Parametrizar modo headless desde línea de comandos.
- Agregar pipeline CI/CD (GitHub Actions / GitLab CI) con reporte publicado como artefacto.
- Configurar múltiples entornos (dev, staging, prod) en `serenity.conf`.

---

## Conclusión

El proyecto implementa un flujo E2E completo y robusto usando **Serenity BDD con Screenplay**, la arquitectura más moderna y mantenible para automatización Java. La separación en capas UI/Task/Question garantiza bajo acoplamiento, facilitando el mantenimiento cuando la UI cambia. La prueba es **autocontenida y reproducible** en cualquier máquina con Java 21 y Chrome.

---

*Prueba Técnica — Jesus Ramirez · 2026*
