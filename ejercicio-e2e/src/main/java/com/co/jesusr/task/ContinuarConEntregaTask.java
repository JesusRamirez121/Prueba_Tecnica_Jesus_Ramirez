package com.co.jesusr.task;

import com.co.jesusr.ui.PaginaCheckoutUI;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;

public class ContinuarConEntregaTask implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {
        // El paso de entrega es opcional: aparece solo si la dirección de entrega
        // es distinta a la de facturación. Se comprueba la presencia del botón
        // en lugar de silenciar excepciones, para que un fallo real no quede oculto.
        if (PaginaCheckoutUI.BOTON_CONTINUAR_ENTREGA.resolveAllFor(actor).isEmpty()) {
            return;
        }
        actor.attemptsTo(Click.on(PaginaCheckoutUI.BOTON_CONTINUAR_ENTREGA));
    }

    public static ContinuarConEntregaTask alSiguientePaso() {
        return Tasks.instrumented(ContinuarConEntregaTask.class);
    }
}
