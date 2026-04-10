package com.co.jesusr.ui;

import net.serenitybdd.screenplay.targets.Target;

public class PaginaCheckoutUI {
    private PaginaCheckoutUI() {}

    // ---- Paso 1: Tipo de cuenta ----
    public static final Target RADIO_INVITADO =
            Target.the("opción Guest Checkout").locatedBy("//input[@value='guest']");

    public static final Target BOTON_CONTINUAR_CUENTA =
            Target.the("botón Continuar (tipo de cuenta)").locatedBy("//input[@id='button-account']");

    // ---- Paso 2: Datos de facturación ----
    public static final Target CAMPO_NOMBRE =
            Target.the("campo Nombre").locatedBy("#input-payment-firstname");

    public static final Target CAMPO_APELLIDO =
            Target.the("campo Apellido").locatedBy("#input-payment-lastname");

    public static final Target CAMPO_EMAIL =
            Target.the("campo Email").locatedBy("#input-payment-email");

    public static final Target CAMPO_TELEFONO =
            Target.the("campo Teléfono").locatedBy("#input-payment-telephone");

    public static final Target CAMPO_DIRECCION =
            Target.the("campo Dirección").locatedBy("#input-payment-address-1");

    public static final Target CAMPO_CIUDAD =
            Target.the("campo Ciudad").locatedBy("#input-payment-city");

    public static final Target CAMPO_CODIGO_POSTAL =
            Target.the("campo Código Postal").locatedBy("#input-payment-postcode");

    public static final Target SELECT_PAIS =
            Target.the("selector de País").locatedBy("#input-payment-country");

    public static final Target SELECT_REGION =
            Target.the("selector de Región/Estado").locatedBy("#input-payment-zone");

    public static final Target BOTON_CONTINUAR_FACTURACION =
            Target.the("botón Continuar (facturación)").locatedBy("#button-guest");

    // ---- Paso 3: Detalles de entrega ----
    public static final Target BOTON_CONTINUAR_ENTREGA =
            Target.the("botón Continuar (entrega)").locatedBy("//input[@id='button-shipping-method']");

    // ---- Paso 4: Método de envío ----
    public static final Target RADIO_METODO_ENVIO =
            Target.the("opción de método de envío").locatedBy("//input[@value='cod']");

    public static final Target BOTON_CONTINUAR_ENVIO =
            Target.the("botón Continuar (método de envío)").locatedBy("//input[@id='button-payment-method']");

    // ---- Paso 5: Método de pago ----
    public static final Target CHECKBOX_TERMINOS =
            Target.the("checkbox Términos y Condiciones").locatedBy("//input[@name='agree']");

    public static final Target BOTON_CONTINUAR_PAGO =
            Target.the("botón Continuar (método de pago)").locatedBy("#button-payment-method");

    // ---- Paso 6: Confirmar pedido ----
    public static final Target BOTON_CONFIRMAR =
            Target.the("botón Confirmar Pedido").locatedBy("#button-confirm");
}

