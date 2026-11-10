package com.co.jesusr.task;

import com.co.jesusr.ui.PaginaCheckoutUI;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.matchers.WebElementStateMatchers;
import net.serenitybdd.screenplay.waits.WaitUntil;

public class SeleccionarPagoYAceptarTerminosTask implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(PaginaCheckoutUI.BOTON_CONTINUAR_PAGO,
                                WebElementStateMatchers.isVisible())
                        .forNoMoreThan(10).seconds()
        );

        // Los términos solo se marcan si el checkbox está presente y visible en esta versión de la tienda.
        var checkboxes = PaginaCheckoutUI.CHECKBOX_TERMINOS.resolveAllFor(actor);
        if (!checkboxes.isEmpty() && checkboxes.get(0).isDisplayed() && !checkboxes.get(0).isSelected()) {
            checkboxes.get(0).click();
        }

        actor.attemptsTo(
                Click.on(PaginaCheckoutUI.BOTON_CONTINUAR_PAGO)
        );
    }

    public static SeleccionarPagoYAceptarTerminosTask yConfirmar() {
        return Tasks.instrumented(SeleccionarPagoYAceptarTerminosTask.class);
    }
}
