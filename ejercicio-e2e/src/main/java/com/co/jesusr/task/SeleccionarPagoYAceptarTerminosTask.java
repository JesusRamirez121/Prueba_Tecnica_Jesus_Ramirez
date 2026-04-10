package com.co.jesusr.task;

import com.co.jesusr.ui.PaginaCheckoutUI;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.matchers.WebElementStateMatchers;
import net.serenitybdd.screenplay.waits.WaitUntil;

public class SeleccionarPagoYAceptarTerminosTask  implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(PaginaCheckoutUI.BOTON_CONTINUAR_PAGO,
                                WebElementStateMatchers.isVisible())
                        .forNoMoreThan(10).seconds()
        );
        try {
            var checkbox = PaginaCheckoutUI.CHECKBOX_TERMINOS.resolveFor(actor);
            if (checkbox.isDisplayed() && !checkbox.isSelected()) {
                checkbox.click();
            }
        } catch (Exception ignorado) {
            // El checkbox puede no estar visible en todos los entornos
        }

        actor.attemptsTo(
                Click.on(PaginaCheckoutUI.BOTON_CONTINUAR_PAGO)
        );
    }

    public static SeleccionarPagoYAceptarTerminosTask yConfirmar() {
        return Tasks.instrumented(SeleccionarPagoYAceptarTerminosTask.class);
    }
}
