# ══════════════════════════════════════════════════════════════════════
# TAREA SCREENPLAY: CrearUsuario
# ══════════════════════════════════════════════════════════════════════
# Equivalente a una "Task" en el patrón Screenplay:
# Encapsula la interacción de bajo nivel POST /user
# El "Actor" llama a esta tarea desde el feature principal.
#
# Variables heredadas del contexto del llamador:
#   baseUrl, username, userId, firstName, lastName,
#   email, password, phone
#
# Variables expuestas al llamador:
#   codigo   → response.code  (debe ser 200)
#   mensaje  → response.message (ID del usuario creado)
# ══════════════════════════════════════════════════════════════════════

@ignore
Feature: Tarea - Crear Usuario en PetStore

  Scenario: Crear un nuevo usuario via POST /user
    Given url baseUrl
    And path '/user'
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
    * def codigo  = response.code
    * def mensaje = response.message
