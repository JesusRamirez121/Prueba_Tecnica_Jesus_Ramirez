
@ignore
Feature: Tarea - Actualizar Usuario en PetStore

  Scenario: Actualizar nombre y email via PUT /user/{username}
    Given url baseUrl
    And path '/user', username
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
    * def codigo  = response.code
    * def mensaje = response.message
