package com.co.jesusr.questions;

import com.co.jesusr.ui.PaginaConfirmacionUI;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;

public class MensajeDeConfirmacionQuestion implements Question<String> {

    @Override
    public String answeredBy(Actor actor) {
        return Text.of(PaginaConfirmacionUI.TITULO_PAGINA)
                .answeredBy(actor);
    }

    public static MensajeDeConfirmacionQuestion enLaPagina() {
        return new MensajeDeConfirmacionQuestion();
    }
}
