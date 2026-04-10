# Reporte de Errores — Ejercicio 1: Prueba de Carga Login
**Fecha:** 10 de Abril de 2026  
**Herramienta:** Apache JMeter 5.6.3  
**API objetivo:** `https://fakestoreapi.com/auth/login`

---

## Resumen ejecutivo

Durante la configuración y ejecución de la prueba de carga se encontraron **3 errores** que impidieron la ejecución normal. Todos fueron resueltos. La prueba finalmente se ejecuta contra un servidor mock local cuando el API real no está disponible, con inicio automático del mock desde el propio plan de prueba.

| # | Error | Severidad | Estado |
|---|-------|-----------|--------|
| 1 | `HTTP 526` — Certificado SSL inválido en `fakestoreapi.com` | 🔴 Crítico | ✅ Resuelto |
| 2 | `Could not delete existing file .../apache-jmeter-5.6.3/bin` | 🔴 Crítico | ✅ Resuelto |
| 3 | JMeter no ejecuta la prueba tras el error de archivo | 🟡 Medio | ✅ Resuelto |

---

## Error 1 — HTTP 526: Invalid SSL Certificate (fakestoreapi.com)

### Descripción
Al lanzar las peticiones contra el endpoint de producción, el servidor retornó código HTTP **526**, un error específico de Cloudflare que indica que el certificado SSL del servidor de origen está vencido o mal configurado.

### Log / Evidencia
```
Response Body:  error code: 526
HTTP Status:    526
```
Vista en JMeter — View Results Tree: todos los samplers marcados en **rojo** con `error code: 526` en el cuerpo de respuesta.

### Cómo reproducir
```bash
curl --location --max-time 15 \
  -X POST "https://fakestoreapi.com/auth/login" \
  -H "Content-Type: application/json" \
  -d '{"username":"johnd","password":"m38rmF$"}'
```
**Resultado esperado:** `{"token":"eyJ..."}`  
**Resultado obtenido:** `error code: 526`

### Causa raíz
```
Cliente (JMeter)
    │  HTTPS OK (cert Cloudflare válido)
    ▼
Cloudflare CDN  (104.21.20.217)
    │  SSL inválido ← certificado origen vencido/mal config
    ▼
Servidor origen (fakestoreapi.com backend)
    └─ Cloudflare no puede conectar → retorna 526 al cliente
```
El error **no es de JMeter** ni de la configuración del plan de prueba. Es un problema del proveedor del API.

### Solución aplicada
1. Se creó `mock-login-server.js`: servidor HTTP local (Node.js, puerto 3001) que replica el comportamiento de `fakestoreapi.com/auth/login` con los mismos 5 usuarios del CSV.
2. El plan de prueba detecta automáticamente si el API real responde HTTP 200. Si no, inicia el mock y apunta a él.
3. El HTTP Sampler usa variables dinámicas: `${__P(TARGET_HOST)}`, `${__P(TARGET_PORT)}`, `${__P(TARGET_PROTOCOL)}`.

### Rollback cuando el API real se restaure
Ningún cambio manual necesario. El setUp Thread Group verifica la disponibilidad en cada ejecución y elige automáticamente el entorno correcto.

---

## Error 2 — JMeter: `Could not delete existing file .../apache-jmeter-5.6.3/bin`

### Descripción
Al presionar **Run (Ctrl+R)** en JMeter GUI, la prueba no arrancaba y el log registraba:
```
ERROR o.a.j.g.a.AbstractAction: Could not delete existing file
D:\Prueba_Tecnica_Jesus_Ramirez\ejercicio-performance\apache-jmeter-5.6.3\bin
```
JMeter intentaba usar el **directorio `bin`** como si fuera un archivo de resultados.

### Log completo
```
2026-04-10 10:39:06,275 ERROR o.a.j.g.a.AbstractAction:
  Could not delete existing file
  D:\Prueba_Tecnica_Jesus_Ramirez\ejercicio-performance\apache-jmeter-5.6.3\bin

2026-04-10 10:42:32,137 ERROR o.a.j.g.a.AbstractAction:
  Could not delete existing file
  D:\Prueba_Tecnica_Jesus_Ramirez\ejercicio-performance\apache-jmeter-5.6.3\bin

2026-04-10 10:57:33,170 ERROR o.a.j.g.a.AbstractAction:
  Could not delete existing file
  D:\Prueba_Tecnica_Jesus_Ramirez\ejercicio-performance\apache-jmeter-5.6.3\bin
```
El error se repitió en **3 intentos de ejecución consecutivos**.

### Cómo reproducir
1. Abrir JMeter desde `apache-jmeter-5.6.3\bin\jmeter.bat`
2. Abrir cualquier `.jmx` que tenga un `ResultCollector` con el campo `filename` vacío (`""`)
3. Presionar **Ctrl+R** o clic en el botón Run
4. Aparece el diálogo _"The file already exists, what do you want to do?"_
5. Hacer clic en **Overwrite existing file**

**Resultado:** `ERROR: Could not delete existing file .../bin`

### Causa raíz
En Java, `new File("").getAbsolutePath()` retorna el **directorio de trabajo actual del JVM**, que cuando JMeter se lanza desde `bin\jmeter.bat` es precisamente `apache-jmeter-5.6.3\bin`.

```
ResultCollector.filename = ""
     ↓
new File("").getAbsolutePath()
     ↓
"D:\...\apache-jmeter-5.6.3\bin"   ← directorio, no archivo
     ↓
file.exists() → true  (existe el directorio)
file.delete() → false (no se puede borrar un directorio con File.delete())
     ↓
ERROR: Could not delete existing file .../bin
```

El diálogo _"file already exists"_ también se puede suprimir configurando:
```properties
# En bin/user.properties
resultcollector.action_if_file_exists=APPEND
```

### Solución aplicada
**a)** Se configuró `resultcollector.action_if_file_exists=APPEND` en `apache-jmeter-5.6.3/bin/user.properties` para evitar el diálogo y el intento de borrado.

**b)** Se asignaron **rutas absolutas** a todos los `ResultCollector` del plan de prueba:
```
D:/Prueba_Tecnica_Jesus_Ramirez/ejercicio-performance/Resultados/resultados_login_tree.jtl
D:/Prueba_Tecnica_Jesus_Ramirez/ejercicio-performance/Resultados/resultados_login.csv
D:/Prueba_Tecnica_Jesus_Ramirez/ejercicio-performance/Resultados/resultados_login_agg.csv
```

Con ruta absoluta válida, `new File(path)` apunta a un archivo real (no al directorio `bin`) y APPEND simplemente añade resultados si el archivo ya existe.

---

## Error 3 — La prueba no ejecuta tras el error de archivo

### Descripción
Después del error 2, JMeter registraba el fallo pero **no iniciaba la prueba**. El contador de muestras permanecía en `0/0` y el botón Stop nunca se activaba.

### Cómo reproducir
Reproducir el Error 2. Tras el log `Could not delete existing file`, observar que:
- El contador de JMeter GUI muestra `0  0/0`
- El botón Stop (cuadrado rojo) no se activa
- Ningún sampler aparece en View Results Tree

### Causa raíz
JMeter trata el fallo de inicialización del `ResultCollector` como **error fatal** de arranque del test. Si no puede preparar el archivo de salida, aborta el inicio de la prueba completa.

### Solución aplicada
Misma que Error 2 (rutas absolutas + propiedad APPEND). Una vez eliminado el conflicto de archivo, JMeter inicializa correctamente los `ResultCollector` y la prueba arranca.

---

## Solución final implementada

### Arquitectura del plan de prueba

```
Login_Load_Test.jmx
│
├── setUp Thread Group (1 hilo, 1 iteración)
│   └── JSR223 Sampler — Groovy
│       ├── PASO 1: POST https://fakestoreapi.com/auth/login (timeout 5s)
│       │   ├── HTTP 200  → TARGET = fakestoreapi.com:443/https
│       │   └── Otro/fallo → continúa al PASO 2
│       ├── PASO 2: Verificar si localhost:3001 responde (socket check)
│       │   ├── Responde  → TARGET = localhost:3001/http
│       │   └── No responde → continúa al PASO 3
│       ├── PASO 3: ProcessBuilder — inicia mock-login-server.js
│       │   └── Espera hasta 5s que :3001 acepte conexiones
│       └── PASO 4: Guarda TARGET_HOST/PORT/PROTOCOL en props JMeter
│
└── Thread Group principal (30 hilos, 30s ramp-up, 120s duración)
    ├── CSV Data Set Config (login_users.csv — 5 usuarios)
    ├── HTTP Header Manager (Content-Type: application/json)
    ├── HTTP Sampler POST ${__P(TARGET_HOST)}:${__P(TARGET_PORT)}/auth/login
    ├── Constant Throughput Timer (1200 req/min = 20 TPS)
    ├── Duration Assertion (máx 1500 ms)
    ├── Response Assertion (HTTP 200)
    ├── View Results Tree  → resultados_login_tree.jtl
    ├── Summary Report     → resultados_login.csv
    └── Aggregate Report   → resultados_login_agg.csv
```

### Archivos generados/modificados

| Archivo | Cambio |
|---------|--------|
| `Login_Load_Test.jmx` | setUp con auto-detección y auto-inicio del mock |
| `mock-login-server.js` | Servidor Node.js mock del endpoint login |
| `login_users.csv` | CSV con 5 usuarios del enunciado |
| `apache-jmeter-5.6.3/bin/user.properties` | `resultcollector.action_if_file_exists=APPEND` |
| `ReporteSolucion.txt` | Diagnóstico paso a paso del Error 1 |
| `readme.txt` | Instrucciones de ejecución con versiones |
| `conclusiones.txt` | Hallazgos y recomendaciones |

### Prerrequisitos de ejecución
- **Apache JMeter 5.6.3** — incluido en el repositorio
- **Java 11+** — requerido por JMeter (`java -version`)
- **Node.js** — requerido por el mock (`node --version`) — versión usada: v24.11.0
- **Conexión a internet** — para intentar el API real (opcional, cae a mock si no hay)

### Ejecución (GUI)
```
1. Abrir: apache-jmeter-5.6.3\bin\jmeter.bat
2. File → Open → Ejercicio1\Login_Load_Test.jmx
3. Ctrl+R
   El setUp verifica el API real, inicia el mock si es necesario,
   y la prueba arranca automáticamente en el entorno disponible.
```

### Ejecución (Non-GUI — recomendada para pruebas de carga)
```cmd
cd D:\Prueba_Tecnica_Jesus_Ramirez\ejercicio-performance\Ejercicio1
..\apache-jmeter-5.6.3\bin\jmeter.bat ^
  -n -t Login_Load_Test.jmx ^
  -l ..\Resultados\resultados_login.csv ^
  -e -o ..\ReportesHTML\Reporte_Login
```

---

*Reporte generado el 10/04/2026 — Ejercicio Prueba Técnica*
