package com.co.jesusr.task;

import com.co.jesusr.ui.PaginaCheckoutUI;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.matchers.WebElementStateMatchers;
import net.serenitybdd.screenplay.waits.WaitUntil;

public class SeleccionarGuestCheckoutTask implements Task {
    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(PaginaCheckoutUI.RADIO_INVITADO,
                                WebElementStateMatchers.isVisible())
                        .forNoMoreThan(10).seconds(),
                Click.on(PaginaCheckoutUI.RADIO_INVITADO),
                Click.on(PaginaCheckoutUI.BOTON_CONTINUAR_CUENTA)
        );
    }

    public static SeleccionarGuestCheckoutTask comoInvitado() {
        return Tasks.instrumented(SeleccionarGuestCheckoutTask.class);
    }
}
