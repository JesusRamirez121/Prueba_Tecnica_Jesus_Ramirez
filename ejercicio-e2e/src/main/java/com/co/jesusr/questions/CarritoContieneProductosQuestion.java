package com.co.jesusr.questions;

import com.co.jesusr.ui.PaginaCarritoUI;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

public class CarritoContieneProductosQuestion implements Question<Integer> {
    @Override
    public Integer answeredBy(Actor actor) {
        return PaginaCarritoUI.FILAS_PRODUCTOS
                .resolveAllFor(actor)
                .size();
    }

    public static CarritoContieneProductosQuestion enLaPagina() {
        return new CarritoContieneProductosQuestion();
    }
}
