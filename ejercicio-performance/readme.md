================================================================
EJERCICIO 1 - PRUEBA DE CARGA: LOGIN FAKESTOREAPI
================================================================

DESCRIPCION:
Prueba de carga sobre el endpoint de login de la FakeStore API
(https://fakestoreapi.com/auth/login), parametrizada con un archivo
CSV de credenciales. El escenario alcanza al menos 20 TPS con
validaciones de tiempo de respuesta y tasa de error.

----------------------------------------------------------------
VERSIONES DE TECNOLOGIAS
----------------------------------------------------------------
- Apache JMeter: 5.6.3
- Java: 11 o superior (requerido por JMeter)
- Sistema Operativo: Windows 10/11

----------------------------------------------------------------
ARCHIVOS DEL EJERCICIO
----------------------------------------------------------------
- Login_Load_Test.jmx   : Plan de prueba JMeter
- login_users.csv       : Datos de entrada (usuarios y contrasenas)
- readme.txt            : Este archivo
- conclusiones.txt      : Hallazgos y conclusiones

----------------------------------------------------------------
PRE-REQUISITOS
----------------------------------------------------------------
1. Tener instalado Java 11 o superior.
   Verificar con: java -version

2. Tener instalado Apache JMeter 5.6.3.
   Descargar desde: https://jmeter.apache.org/download_jmeter.cgi
   O usar la instalacion incluida en:
   ..\apache-jmeter-5.6.3\

3. Asegurarse de tener conexion a internet para acceder a:
   https://fakestoreapi.com/auth/login

----------------------------------------------------------------
INSTRUCCIONES DE EJECUCION
----------------------------------------------------------------

OPCION A - Ejecucion desde interfaz grafica (GUI):
--------------------------------------------------
1. Abrir JMeter:
   ..\apache-jmeter-5.6.3\bin\jmeter.bat

2. Ir a File > Open y seleccionar:
   Login_Load_Test.jmx

3. En el nodo "CSV Data Set Config - Usuarios", verificar que
   el campo "Filename" apunte correctamente a login_users.csv
   (puede ser ruta relativa o absoluta).

4. Hacer clic en el boton "Start" (triangulo verde) o
   presionar Ctrl+R para iniciar la prueba.

5. Revisar resultados en los listeners:
   - "View Results Tree"   : detalle por peticion
   - "Summary Report"      : resumen de metricas
   - "Aggregate Report"    : estadisticas agregadas

OPCION B - Ejecucion por linea de comandos (modo Non-GUI):
----------------------------------------------------------
1. Abrir una terminal (CMD o PowerShell).

2. Navegar al directorio del ejercicio:
   cd D:\Prueba_Tecnica_Jesus_Ramirez\ejercicio-performance\Ejercicio1

3. Ejecutar el siguiente comando:

   ..\apache-jmeter-5.6.3\bin\jmeter.bat ^
     -n ^
     -t Login_Load_Test.jmx ^
     -l ..\Resultados\resultados_login.csv ^
     -e ^
     -o ..\ReportesHTML\Reporte_Login

   Parametros:
     -n           : modo non-GUI
     -t           : archivo .jmx de entrada
     -l           : archivo CSV donde se guardan los resultados
     -e           : generar reporte HTML al finalizar
     -o           : directorio de salida del reporte HTML
                    (debe estar vacio o no existir)

4. Una vez finalizada la prueba, abrir el reporte HTML:
   ..\ReportesHTML\Reporte_Login\index.html

----------------------------------------------------------------
ESCENARIO DE LA PRUEBA
----------------------------------------------------------------
- Endpoint    : POST https://fakestoreapi.com/auth/login
- Usuarios    : 30 hilos (threads) concurrentes
- Ramp-up     : 30 segundos
- Duracion    : 120 segundos
- TPS objetivo: 20 TPS (controlado con Constant Throughput Timer
                configurado en 1200 req/min)
- Datos CSV   : login_users.csv (5 usuarios, reciclados en loop)

Validaciones configuradas:
  1. Tiempo de respuesta maximo: 1500 ms (DurationAssertion)
  2. Codigo de respuesta HTTP: 200 (ResponseAssertion)
  3. Tasa de error aceptable:  < 3% (monitoreada en reportes)

----------------------------------------------------------------
NOTAS ADICIONALES
----------------------------------------------------------------
- El archivo login_users.csv contiene credenciales validas para
  la FakeStore API en modo demo. El API acepta cualquier usuario
  registrado y retorna un token JWT.

- Si al ejecutar en modo Non-GUI el directorio de reporte ya
  existe, eliminarlo primero:
  rmdir /s /q ..\ReportesHTML\Reporte_Login

- Para modificar la duracion o cantidad de hilos, editar el
  archivo .jmx con JMeter GUI o con un editor de texto XML.
================================================================
