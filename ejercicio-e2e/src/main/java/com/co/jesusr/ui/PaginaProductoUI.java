package com.co.jesusr.ui;

import net.serenitybdd.screenplay.targets.Target;

public class PaginaProductoUI {
    private PaginaProductoUI() {}

    public static final Target BOTON_AGREGAR_CARRITO =
            Target.the("botón Agregar al Carrito").locatedBy("//button[@id='button-cart']");

    public static final Target MENSAJE_EXITO =
            Target.the("mensaje de éxito al agregar").locatedBy("//div[@class='alert alert-success alert-dismissible']");
}

