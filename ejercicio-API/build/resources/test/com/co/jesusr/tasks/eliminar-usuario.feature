# ══════════════════════════════════════════════════════════════════════
# TAREA SCREENPLAY: EliminarUsuario
# ══════════════════════════════════════════════════════════════════════
# Encapsula la interacción DELETE /user/{username}
# Esta tarea es llamada desde TC-005 demostrando el patrón Screenplay:
#   Actor → llama a → Tarea → ejecuta → Interacción HTTP DELETE
#
# Variables heredadas del contexto del llamador:
#   baseUrl, username
#
# Variables expuestas al llamador:
#   codigo  → response.code    (debe ser 200)
#   mensaje → response.message (username del usuario eliminado)
# ══════════════════════════════════════════════════════════════════════

@ignore
Feature: Tarea - Eliminar Usuario en PetStore

  Scenario: Eliminar usuario via DELETE /user/{username}
    Given url baseUrl
    And path '/user', username
    And header Accept = 'application/json'
    When method DELETE
    Then status 200
    * def codigo  = response.code
    * def mensaje = response.message
