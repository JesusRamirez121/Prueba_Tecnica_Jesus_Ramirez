package com.co.jesusr.task;

import com.co.jesusr.ui.ErrorCertificadoDigitalIExplorerUi;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.markers.IsSilent;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class SitioNoSeguroTask implements Task, IsSilent {
    private static final Logger logger = LoggerFactory.getLogger(SitioNoSeguroTask.class);

    public static Performable aceptarIngresoAUrl() {
        return instrumented(SitioNoSeguroTask.class);
    }

    @Override
    @Step("{0} ignora el error de Certificado Digital y accede a la url")
    public <T extends Actor> void performAs(T actor) {
        boolean errorCertificado = true;
        logger.info("ErrorCertificado-Entro a validar si despliega Sitio No Seguro");
        try {
            BrowseTheWeb.as(actor)
                    .withTimeoutOf(Duration.ofSeconds(6))
                    .waitFor(ExpectedConditions.titleIs("Error de privacidad"));
        } catch (Exception e) {
            errorCertificado = false;
            logger.info("ErrorCertificado-Desplego directamente la página errorCertificado");
        }
        if (errorCertificado) {
            actor.attemptsTo(
                    Click.on(ErrorCertificadoDigitalIExplorerUi.OPCIONES_AVANZADAS_LINK),
                    WaitUntil.the(ErrorCertificadoDigitalIExplorerUi.CONTINUAR_PAGINA_LINK, isVisible())
                            .forNoMoreThan(Duration.ofSeconds(5)),
                    Click.on(ErrorCertificadoDigitalIExplorerUi.CONTINUAR_PAGINA_LINK));
        }
    }
}
