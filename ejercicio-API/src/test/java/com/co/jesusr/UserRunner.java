package com.co.jesusr;

import com.intuit.karate.junit5.Karate;
import org.junit.jupiter.api.DisplayName;

@DisplayName("PetStore - Gestión de Usuarios")
class UserRunner {

    @Karate.Test
    @DisplayName("CRUD completo de usuario con patrón Screenplay")
    Karate testGestionUsuarios() {
        return Karate.run("user")
                     .relativeTo(getClass());
    }
}
