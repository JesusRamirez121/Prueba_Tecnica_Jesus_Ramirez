# Ejercicios de Performance — JMeter (y k6)

Este directorio contiene los planes de prueba de rendimiento. Solo el **Ejercicio 1** está documentado en detalle aquí; el resto se describe al final.

## Ejercicio 1 — Carga sobre el login de FakeStore API

Carpeta: `Ejercicio1/`. Plan: `Login_Load_Test.jmx`. Datos: `login_users.csv`.

### Requisitos
- Java 11 o superior.
- Apache JMeter 5.6.3 (incluido en `apache-jmeter-5.6.3/`).
- **Node.js** (solo si el API real no responde y el plan debe usar el mock local).

### Escenario
- Endpoint: `POST https://fakestoreapi.com/auth/login`
- 30 hilos, ramp-up 30 s, duración 120 s.
- Constant Throughput Timer a 1200 req/min (20 TPS).
- Validaciones: respuesta ≤ 1500 ms y código HTTP **200 o 201** (FakeStore responde 201 en login exitoso).

### Cómo se elige el entorno
Antes de la carga, un script JSR223 hace una petición de prueba a `fakestoreapi.com`:
- Si responde 200 o 201, la prueba apunta a la API real.
- Si no, arranca `mock-login-server.js` en `localhost:3001` y la prueba apunta al mock. **El resultado de la prueba indica en qué entorno se ejecutó** (`API_ENV` / mensaje del sampler de setup). Revísalo antes de interpretar los resultados.

### Ejecución
Ejecutar **desde la carpeta `Ejercicio1`**, porque las rutas del plan son relativas:

```bash
cd ejercicio-performance/Ejercicio1
../apache-jmeter-5.6.3/bin/jmeter -n -t Login_Load_Test.jmx -l ../Resultados/resultados_login.csv -e -o ../ReportesHTML/Reporte_Login
```

- El reporte HTML queda en `ReportesHTML/Reporte_Login/index.html`. El directorio de salida no debe existir previamente (bórralo si ya existe).
- En Windows usa `jmeter.bat`.

## Otros ejercicios de este directorio

- `Pruebas/`: 22 planes `.jmx` de práctica (correlaciones, cookies, uploads, JSON extractor, etc.). Sin documentación individual.
- `Ejercicio2/`: resumen de ejecución de **k6** (`textSummary.txt`) e informe (`InformeResultados.docx`). El script de k6 no está en el repositorio.
- `JsonServer/`: notas para levantar un json-server local de práctica.
- `Data_csv/`: datos de ejemplo para los planes de `Pruebas/`.
- `Conector/` y `apache-jmeter-5.6.3/`: binarios vendidos (driver MySQL y JMeter).
