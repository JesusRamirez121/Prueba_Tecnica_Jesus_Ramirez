package com.co.jesusr.task;

import com.co.jesusr.ui.PaginaCheckoutUI;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.SelectFromOptions;
import net.serenitybdd.screenplay.matchers.WebElementStateMatchers;
import net.serenitybdd.screenplay.waits.WaitUntil;

public class CompletarFormularioFacturacionTask implements Task {
    private static final String NOMBRE        = "Juan";
    private static final String APELLIDO      = "Perez";
    private static final String EMAIL         = "juan.perez.test@mailtest.com";
    private static final String TELEFONO      = "3001234567";
    private static final String DIRECCION     = "Calle 123 # 45-67";
    private static final String CIUDAD        = "Bogota";
    private static final String CODIGO_POSTAL = "110111";
    private static final String PAIS          = "Colombia";

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                // Esperar que el formulario sea visible
                WaitUntil.the(PaginaCheckoutUI.CAMPO_NOMBRE,
                                WebElementStateMatchers.isVisible())
                        .forNoMoreThan(10).seconds(),

                // Rellenar campos de texto
                Enter.theValue(NOMBRE).into(PaginaCheckoutUI.CAMPO_NOMBRE),
                Enter.theValue(APELLIDO).into(PaginaCheckoutUI.CAMPO_APELLIDO),
                Enter.theValue(EMAIL).into(PaginaCheckoutUI.CAMPO_EMAIL),
                Enter.theValue(TELEFONO).into(PaginaCheckoutUI.CAMPO_TELEFONO),
                Enter.theValue(DIRECCION).into(PaginaCheckoutUI.CAMPO_DIRECCION),
                Enter.theValue(CIUDAD).into(PaginaCheckoutUI.CAMPO_CIUDAD),
                Enter.theValue(CODIGO_POSTAL).into(PaginaCheckoutUI.CAMPO_CODIGO_POSTAL),

                // Seleccionar país (dispara carga AJAX de regiones)
                SelectFromOptions.byVisibleText(PAIS).from(PaginaCheckoutUI.SELECT_PAIS),

                // Esperar que el select de región se habilite tras el AJAX
                WaitUntil.the(PaginaCheckoutUI.SELECT_REGION,
                                WebElementStateMatchers.isEnabled())
                        .forNoMoreThan(10).seconds(),

                // Seleccionar primera región disponible
                SelectFromOptions.byIndex(1).from(PaginaCheckoutUI.SELECT_REGION),

                // Continuar al siguiente paso
                Click.on(PaginaCheckoutUI.BOTON_CONTINUAR_FACTURACION)
        );
    }

    public static CompletarFormularioFacturacionTask conDatosDePrueba() {
        return Tasks.instrumented(CompletarFormularioFacturacionTask.class);
    }
}
