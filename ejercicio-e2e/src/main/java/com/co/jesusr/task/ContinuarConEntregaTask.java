package com.co.jesusr.task;

import com.co.jesusr.ui.PaginaCheckoutUI;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

public class ContinuarConEntregaTask implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {
        try {
            PaginaCheckoutUI.BOTON_CONTINUAR_ENTREGA
                    .resolveFor(actor)
                    .click();
        } catch (Exception ignorado) {
            // El paso de entrega puede estar ausente si la dirección
            // de entrega coincide con la de facturación.
        }
    }

    public static ContinuarConEntregaTask alSiguientePaso() {
        return Tasks.instrumented(ContinuarConEntregaTask.class);
    }
}