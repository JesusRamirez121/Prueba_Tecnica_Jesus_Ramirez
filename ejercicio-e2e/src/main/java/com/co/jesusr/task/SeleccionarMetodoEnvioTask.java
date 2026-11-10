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

        // Si la opción de envío ya viene preseleccionada no hay nada que elegir.
        var radios = PaginaCheckoutUI.RADIO_METODO_ENVIO.resolveAllFor(actor);
        if (!radios.isEmpty() && !radios.get(0).isSelected()) {
            radios.get(0).click();
        }

        actor.attemptsTo(
                Click.on(PaginaCheckoutUI.BOTON_CONTINUAR_ENVIO)
        );
    }

    public static SeleccionarMetodoEnvioTask disponible() {
        return Tasks.instrumented(SeleccionarMetodoEnvioTask.class);
    }
}
