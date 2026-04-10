/**
 * karate-config.js
 * Configuración global de Karate para la suite de pruebas PetStore.
 * Este archivo es el equivalente al "Stage" en el patrón Screenplay:
 * define el entorno y las capacidades disponibles para todos los actores.
 */
function fn() {
    var config = {
        // URL base de la API PetStore
        baseUrl: 'https://petstore.swagger.io/v2',

        // Datos del usuario de prueba (compartidos entre todos los escenarios)
        username:  'jesusr_karate_test',
        userId:    9100001,
        firstName: 'Jesus',
        lastName:  'Ramirez',
        email:     'jesus.ramirez@karate.test',
        password:  'Karate2024!',
        phone:     '3001234567',

        // Datos actualizados (usados en TC-003 y TC-004)
        updatedFirstName: 'Jesus Carlos',
        updatedLastName:  'Ramirez Gomez',
        updatedEmail:     'jesus.carlos@karate.updated'
    };

    // Configuración de timeouts y SSL
    karate.configure('connectTimeout', 15000);
    karate.configure('readTimeout',    15000);
    karate.configure('ssl', true);
    karate.configure('charset', null);

    return config;
}
