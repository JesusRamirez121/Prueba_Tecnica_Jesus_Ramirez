package com.co.jesusr.task;

import com.co.jesusr.ui.PaginaPrincipalUI;
import com.co.jesusr.ui.PaginaProductoUI;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.matchers.WebElementStateMatchers;
import net.serenitybdd.screenplay.waits.WaitUntil;

public class BuscarYAgregarProductoTask implements Task {

    private final String nombreProducto;

    public BuscarYAgregarProductoTask(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Enter.theValue(nombreProducto).into(PaginaPrincipalUI.CAMPO_BUSQUEDA),
                Click.on(PaginaPrincipalUI.BOTON_BUSCAR),
                WaitUntil.the(PaginaPrincipalUI.enlaceProducto(nombreProducto),
                                WebElementStateMatchers.isVisible())
                        .forNoMoreThan(10).seconds(),
                Click.on(PaginaPrincipalUI.enlaceProducto(nombreProducto)),
                WaitUntil.the(PaginaProductoUI.BOTON_AGREGAR_CARRITO,
                                WebElementStateMatchers.isClickable())
                        .forNoMoreThan(10).seconds(),
                Click.on(PaginaProductoUI.BOTON_AGREGAR_CARRITO),
                WaitUntil.the(PaginaProductoUI.MENSAJE_EXITO,
                                WebElementStateMatchers.isVisible())
                        .forNoMoreThan(10).seconds()
        );
    }

    public static BuscarYAgregarProductoTask llamado(String nombreProducto) {
        return Tasks.instrumented(BuscarYAgregarProductoTask.class, nombreProducto);
    }
}
