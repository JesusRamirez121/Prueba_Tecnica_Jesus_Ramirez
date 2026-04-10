package com.co.jesusr.ui;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class ErrorCertificadoDigitalIExplorerUi extends PageObject {

    public static final Target OPCIONES_AVANZADAS_LINK =
            Target.the("Link Más Información").locatedBy("//button[@id='details-button']");

    public static final Target CONTINUAR_PAGINA_LINK =
            Target.the("Link Continuar en la página web (no recomendado)").locatedBy("//a[@class='small-link']");

}
