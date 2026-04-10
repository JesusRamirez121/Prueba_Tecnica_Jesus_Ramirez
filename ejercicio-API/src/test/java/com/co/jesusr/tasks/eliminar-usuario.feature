
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
