package com.co.jesusr.task;

import com.co.jesusr.ui.PaginaCarritoUI;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.matchers.WebElementStateMatchers;
import net.serenitybdd.screenplay.waits.WaitUntil;

public class ProcederAlCheckoutTask implements Task {
    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(PaginaCarritoUI.BOTON_CHECKOUT,
                                WebElementStateMatchers.isClickable())
                        .forNoMoreThan(10).seconds(),
                Click.on(PaginaCarritoUI.BOTON_CHECKOUT)
        );
    }

    public static ProcederAlCheckoutTask desdeElCarrito() {
        return Tasks.instrumented(ProcederAlCheckoutTask.class);
    }
}
