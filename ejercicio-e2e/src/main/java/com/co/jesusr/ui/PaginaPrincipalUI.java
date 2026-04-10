package com.co.jesusr.ui;

import net.serenitybdd.annotations.DefaultUrl;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.screenplay.targets.Target;

@DefaultUrl("http://opencart.abstracta.us/")
public class PaginaPrincipalUI extends PageObject {
    public static final Target CAMPO_BUSQUEDA =
            Target.the("campo de búsqueda").locatedBy("//input[@name='search']");

    public static final Target BOTON_BUSCAR =
            Target.the("botón buscar").locatedBy("//button[@class='btn btn-default btn-lg']");

    public static final Target BOTON_CARRITO =
            Target.the("botón del carrito").locatedBy("//span[@id='cart-total']");

    public static final Target ENLACE_VER_CARRITO =
            Target.the("enlace Ver Carrito")
                    .locatedBy("//strong[normalize-space()='View Cart']");

    public static Target enlaceProducto(String nombre) {
        return Target.the("enlace del producto '" + nombre + "'")
                .locatedBy("//a[normalize-space()='" + nombre + "']");
    }
}
