package com.co.jesusr.task;

import com.co.jesusr.ui.PaginaCheckoutUI;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.matchers.WebElementStateMatchers;
import net.serenitybdd.screenplay.waits.WaitUntil;

public class SeleccionarMetodoEnvioTask implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(PaginaCheckoutUI.BOTON_CONTINUAR_ENVIO,
                                WebElementStateMatchers.isVisible())
                        .forNoMoreThan(10).seconds()
        );

        try {
            var radioEnvio = PaginaCheckoutUI.RADIO_METODO_ENVIO.resolveFor(actor);
            if (!radioEnvio.isSelected()) {
                radioEnvio.click();
            }
        } catch (Exception ignorado) {
            // Ya hay una opción de envío preseleccionada
        }

        actor.attemptsTo(
                Click.on(PaginaCheckoutUI.BOTON_CONTINUAR_ENVIO)
        );
    }

    public static SeleccionarMetodoEnvioTask disponible() {
        return Tasks.instrumented(SeleccionarMetodoEnvioTask.class);
    }
}
