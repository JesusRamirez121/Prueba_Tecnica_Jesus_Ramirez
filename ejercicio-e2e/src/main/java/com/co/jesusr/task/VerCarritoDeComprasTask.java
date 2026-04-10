package com.co.jesusr.task;

import com.co.jesusr.ui.PaginaPrincipalUI;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.matchers.WebElementStateMatchers;
import net.serenitybdd.screenplay.waits.WaitUntil;

public class VerCarritoDeComprasTask implements Task {

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Click.on(PaginaPrincipalUI.BOTON_CARRITO),
                WaitUntil.the(PaginaPrincipalUI.ENLACE_VER_CARRITO,
                                WebElementStateMatchers.isVisible())
                        .forNoMoreThan(10).seconds(),
                Click.on(PaginaPrincipalUI.ENLACE_VER_CARRITO)
        );
    }

    public static VerCarritoDeComprasTask enLaCabecera() {
        return Tasks.instrumented(VerCarritoDeComprasTask.class);
    }
}
