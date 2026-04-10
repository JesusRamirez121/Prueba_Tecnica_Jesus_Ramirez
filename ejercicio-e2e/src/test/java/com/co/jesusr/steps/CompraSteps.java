package com.co.jesusr.steps;

import com.co.jesusr.questions.CarritoContieneProductosQuestion;
import com.co.jesusr.questions.MensajeDeConfirmacionQuestion;
import com.co.jesusr.task.*;
import com.co.jesusr.ui.PaginaPrincipalUI;
import io.cucumber.java.Before;
import io.cucumber.java.es.*;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;

import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;


public class CompraSteps {
    @Before
    public void prepararEscenario() {
        OnStage.setTheStage(new OnlineCast());
    }

    @Dado("que el usuario está en la página principal de OpenCart")
    public void elUsuarioEstaEnLaPaginaPrincipal() {
        theActorCalled("ATM").attemptsTo(
                Open.browserOn(new PaginaPrincipalUI())
        );
        theActorInTheSpotlight().usingAbilityTo(BrowseTheWeb.class).getDriver().manage().window().maximize();
    }

    @Cuando("el usuario agrega el producto {string} al carrito de compras")
    public void agregaElProductoAlCarrito(String nombreProducto) {
        theActorInTheSpotlight().attemptsTo(BuscarYAgregarProductoTask.llamado(nombreProducto));

    }

    @Cuando("visualiza el carrito de compras")
    public void visualizaElCarrito() {
        theActorInTheSpotlight().attemptsTo(
                VerCarritoDeComprasTask.enLaCabecera()
        );
    }

    @Cuando("procede al proceso de checkout")
    public void procedeAlCheckout() {
        theActorInTheSpotlight().attemptsTo(
                ProcederAlCheckoutTask.desdeElCarrito()
        );
    }

    @Cuando("elige continuar como invitado")
    public void eligeContinuarComoInvitado() {
        theActorInTheSpotlight().wasAbleTo(SitioNoSeguroTask.aceptarIngresoAUrl());
        theActorInTheSpotlight().attemptsTo(
                SeleccionarGuestCheckoutTask.comoInvitado()
        );
    }

    @Cuando("completa los datos de facturación del invitado")
    public void completaLosDatosDeFacturacion() {
        theActorInTheSpotlight().attemptsTo(
                CompletarFormularioFacturacionTask.conDatosDePrueba()
        );
    }

    @Cuando("continúa con los detalles de entrega")
    public void continuaConEntrega() {
        theActorInTheSpotlight().attemptsTo(
                ContinuarConEntregaTask.alSiguientePaso()
        );
    }

    @Cuando("selecciona el método de envío disponible")
    public void seleccionaMetodoEnvio() {
        theActorInTheSpotlight().attemptsTo(
                SeleccionarMetodoEnvioTask.disponible()
        );
    }

    @Cuando("selecciona el método de pago y acepta los términos")
    public void seleccionaMetodoPagoYAceptaTerminos() {
        theActorInTheSpotlight().attemptsTo(
                SeleccionarPagoYAceptarTerminosTask.yConfirmar()
        );
    }

    @Cuando("confirma el pedido")
    public void confirmaPedido() {
        theActorInTheSpotlight().attemptsTo(
                ConfirmarPedidoTask.ahora()
        );
    }


    @Entonces("el carrito muestra los productos agregados")
    public void elCarritoMuestraLosProductos() {
        int cantidad = CarritoContieneProductosQuestion.enLaPagina()
                .answeredBy(theActorInTheSpotlight());
        assertThat(
                "El carrito debería contener al menos 2 productos",
                cantidad,
                greaterThanOrEqualTo(2)
        );
    }

    @Entonces("debe ver el mensaje {string}")
    public void debeVerElMensaje(String mensajeEsperado) {
        String tituloActual = MensajeDeConfirmacionQuestion.enLaPagina()
                .answeredBy(theActorInTheSpotlight());
        assertThat(
                "Mensaje de confirmación incorrecto",
                tituloActual,
                containsString(mensajeEsperado)
        );
    }
}

