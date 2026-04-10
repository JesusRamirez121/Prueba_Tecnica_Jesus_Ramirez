package com.co.jesusr.task;

import com.co.jesusr.ui.PaginaCheckoutUI;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.matchers.WebElementStateMatchers;
import net.serenitybdd.screenplay.waits.WaitUntil;

public class ConfirmarPedidoTask implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(PaginaCheckoutUI.BOTON_CONFIRMAR,
                                WebElementStateMatchers.isClickable())
                        .forNoMoreThan(10).seconds(),
                Click.on(PaginaCheckoutUI.BOTON_CONFIRMAR)
        );
    }

    public static ConfirmarPedidoTask ahora() {
        return Tasks.instrumented(ConfirmarPedidoTask.class);
    }
}
