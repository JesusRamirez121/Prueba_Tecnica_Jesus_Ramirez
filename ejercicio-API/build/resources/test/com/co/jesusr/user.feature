Feature: Gestión de Usuarios - PetStore API

  # ── Antecedentes: configura el contexto compartido para todos los TCs ──
  Background:
    * url baseUrl
    # Datos del usuario (vienen de karate-config.js)
    # username, userId, firstName, lastName, email, password, phone
    # updatedFirstName, updatedLastName, updatedEmail

  # ════════════════════════════════════════════════════════════════
  # TC-001: Crear un usuario
  # Método:  POST /user
  # Entrada: objeto User completo (id, username, nombres, email, etc.)
  # Salida:  { code: 200, type: "unknown", message: "<userId>" }
  # ════════════════════════════════════════════════════════════════
  Scenario: TC-001 Crear un usuario en PetStore

    Given path '/user'
    And header Content-Type = 'application/json'
    And request
      """
      {
        "id":         #(userId),
        "username":   "#(username)",
        "firstName":  "#(firstName)",
        "lastName":   "#(lastName)",
        "email":      "#(email)",
        "password":   "#(password)",
        "phone":      "#(phone)",
        "userStatus": 1
      }
      """
    When method POST
    Then status 200

    # ── PREGUNTAS (verificaciones) ──
    And match response         == { code: 200, type: 'unknown', message: '#notnull' }
    And match response.code    == 200
    And match response.type    == 'unknown'
    And match response.message != null

    * print '✅ TC-001 PASSED | Usuario creado | ID:', response.message

  # ════════════════════════════════════════════════════════════════
  # TC-002: Buscar el usuario creado
  # Método:  GET /user/{username}
  # Entrada: username (path param)
  # Salida:  objeto User con todos los campos
  # ════════════════════════════════════════════════════════════════
  Scenario: TC-002 Buscar el usuario creado

    Given path '/user', username
    And header Accept = 'application/json'
    When method GET
    Then status 200

    # ── PREGUNTAS (verificaciones) ──
    And match response.id        == userId
    And match response.username  == username
    And match response.firstName == firstName
    And match response.lastName  == lastName
    And match response.email     == email
    And match response.phone     == phone
    And match response.userStatus == 1

    * print '✅ TC-002 PASSED | Usuario encontrado:', response.username

  # ════════════════════════════════════════════════════════════════
  # TC-003: Actualizar nombre y correo del usuario
  # Método:  PUT /user/{username}
  # Entrada: objeto User con firstName y email actualizados
  # Salida:  { code: 200, type: "unknown", message: "<userId>" }
  # ════════════════════════════════════════════════════════════════
  Scenario: TC-003 Actualizar nombre y correo del usuario

    Given path '/user', username
    And header Content-Type = 'application/json'
    And request
      """
      {
        "id":         #(userId),
        "username":   "#(username)",
        "firstName":  "#(updatedFirstName)",
        "lastName":   "#(updatedLastName)",
        "email":      "#(updatedEmail)",
        "password":   "#(password)",
        "phone":      "#(phone)",
        "userStatus": 1
      }
      """
    When method PUT
    Then status 200

    # ── PREGUNTAS (verificaciones) ──
    And match response.code == 200
    And match response.type == 'unknown'

    * print '✅ TC-003 PASSED | Usuario actualizado | Nuevo nombre:', updatedFirstName, '| Nuevo email:', updatedEmail

  # ════════════════════════════════════════════════════════════════
  # TC-004: Buscar el usuario actualizado
  # Método:  GET /user/{username}
  # Entrada: username (path param)
  # Salida:  objeto User con firstName y email actualizados
  # ════════════════════════════════════════════════════════════════
  Scenario: TC-004 Buscar el usuario actualizado

    Given path '/user', username
    And header Accept = 'application/json'
    When method GET
    Then status 200

    # ── PREGUNTAS (verificaciones) ──
    And match response.username   == username
    And match response.firstName  == updatedFirstName
    And match response.lastName   == updatedLastName
    And match response.email      == updatedEmail

    * print '✅ TC-004 PASSED | Datos actualizados confirmados | firstName:', response.firstName, '| email:', response.email

  # ════════════════════════════════════════════════════════════════
  # TC-005: Eliminar el usuario — Karate + Patrón Screenplay
  # ════════════════════════════════════════════════════════════════
  # Aquí se aplica explícitamente el patrón Screenplay:
  #
  #   ACTOR  → theActorInTheSpotlight() (el tester de API)
  #   TAREA  → EliminarUsuario encapsulada en tasks/eliminar-usuario.feature
  #
  # El Actor no interactúa directamente con el HTTP DELETE.
  # En cambio, delega esa responsabilidad a la TAREA, que sí conoce
  # los detalles de la interacción (endpoint, headers, método HTTP).
  # Esto refleja exactamente el patrón Screenplay:
  #   Actor.attemptsTo(EliminarUsuario.conUsername(username))
  #
  # Método:  DELETE /user/{username}  (ejecutado desde la Tarea)
  # Entrada: username (heredado del Background → karate-config.js)
  # Salida:  { code: 200, type: "unknown", message: "<username>" }
  # ════════════════════════════════════════════════════════════════
  Scenario: TC-005 Eliminar el usuario (Karate + Patrón Screenplay)

    # ── El Actor delega la eliminación a la Tarea ──
    * def resultado = call read('tasks/eliminar-usuario.feature')

    # ── PREGUNTAS: el Actor verifica el estado del sistema ──
    * match resultado.codigo  == 200
    * match resultado.mensaje == username

    * print '✅ TC-005 PASSED | Actor ejecutó Tarea EliminarUsuario | Usuario eliminado:', resultado.mensaje

  # ════════════════════════════════════════════════════════════════
  # TC-006: Verificar que el usuario fue eliminado correctamente
  # Método:  GET /user/{username}
  # Entrada: username (path param)
  # Salida:  HTTP 404 (el usuario ya no existe)
  # ════════════════════════════════════════════════════════════════
  Scenario: TC-006 Verificar que el usuario fue eliminado correctamente

    Given path '/user', username
    And header Accept = 'application/json'
    When method GET
    Then status 404

    * print '✅ TC-006 PASSED | Confirmado: usuario', username, 'ya no existe en el sistema'
