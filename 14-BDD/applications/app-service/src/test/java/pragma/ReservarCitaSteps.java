package pragma;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pragma.usecase.GestionarCitasUseCase;

/**
 * TODO: conecta cada paso del .feature con GestionarCitasUseCase real.
 * Guarda la excepción capturada (si la hay) en un campo, igual que en el
 * ejemplo RetiroSteps del documento de retos, para poder verificarla en el Then.
 */
public class ReservarCitaSteps {
    private final GestionarCitasUseCase useCase = new GestionarCitasUseCase();
    private Exception errorCapturado;

    // TODO: @Given(...) que deje el sistema en el estado inicial que necesites

    // TODO: @When(...) que llame a useCase.reservar(...) y capture la excepción si la hay

    // TODO: @Then(...) que verifique el resultado esperado (cita creada, o el mensaje de error)
}
