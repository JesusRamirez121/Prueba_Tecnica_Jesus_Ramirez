
@ignore
Feature: Tarea - Buscar Usuario en PetStore

  Scenario: Buscar usuario por username via GET /user/{username}
    Given url baseUrl
    And path '/user', username
    And header Accept = 'application/json'
    When method GET
    Then status 200
    * def usuario = response
