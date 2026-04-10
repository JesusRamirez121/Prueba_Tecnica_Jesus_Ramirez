package com.co.jesusr.ui;

import net.serenitybdd.screenplay.targets.Target;

public class PaginaCarritoUI {
    private PaginaCarritoUI() {}
    public static final Target FILAS_PRODUCTOS =
            Target.the("filas de productos en el carrito")
                    .locatedBy("table.table tbody tr");

    public static final Target BOTON_CHECKOUT =
            Target.the("botón Checkout")
                    .locatedBy("//a[@class='btn btn-primary']");
}
