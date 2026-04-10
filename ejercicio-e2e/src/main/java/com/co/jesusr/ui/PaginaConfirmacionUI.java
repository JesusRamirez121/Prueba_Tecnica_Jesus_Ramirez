package com.co.jesusr.ui;

import net.serenitybdd.screenplay.targets.Target;

public class PaginaConfirmacionUI {
    private PaginaConfirmacionUI () {
    }
    public static final Target TITULO_PAGINA =
            Target.the("título de confirmación del pedido").locatedBy("//h1[normalize-space()='Your order has been placed!']");

}
