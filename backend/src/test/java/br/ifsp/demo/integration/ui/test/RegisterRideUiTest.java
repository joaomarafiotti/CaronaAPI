package br.ifsp.demo.integration.ui.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Dimension;

import br.ifsp.demo.integration.ui.BaseSeleniumTest;
import br.ifsp.demo.integration.ui.page.RegisterRidePage;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("UiTest")
public class RegisterRideUiTest extends BaseSeleniumTest {

    @Override
    protected void setInitialPage() {
        new RegisterRidePage(driver); // Começa sempre na página de cadastro de carona
    }

    @Test
    @DisplayName("Happy Path - Should register ride with valid data")
    void shouldRegisterRideWithValidData() {
        RegisterRidePage registerRidePage = new RegisterRidePage(driver);
        registerRidePage.fillStartAddress("Rua A, 123, Centro, São Paulo");
        registerRidePage.fillEndAddress("Rua B, 456, Bairro X, São Paulo");

        // Preenche um horário futuro válido
        String futureDateTime = java.time.LocalDateTime.now().plusDays(1).withHour(10).withMinute(0).toString().replace("T", " ");
        String formattedFutureDateTime = futureDateTime.replace(" ", "T").substring(0, 16);
        registerRidePage.fillDepartureTime(formattedFutureDateTime);

        // Valor do carId que aparece no select (ex: "1" ou "2", etc)
        registerRidePage.selectCar("1");

        registerRidePage.submitForm();

        // Esperado: mostrar mensagem de sucesso
        assertThat(registerRidePage.isFormSuccessVisible()).isTrue();
        assertThat(registerRidePage.getFormSuccessText()).contains("Ride registered successfully!");
    }

    @Test
    @DisplayName("Sad Path - Should show error when submitting empty form")
    void shouldShowErrorWhenSubmittingEmptyForm() {
        RegisterRidePage registerRidePage = new RegisterRidePage(driver);

        registerRidePage.submitForm();

        // Deve exibir form-error com mensagem
        assertThat(registerRidePage.isFormErrorVisible()).isTrue();
        assertThat(registerRidePage.getFormErrorText()).contains("Please fill in all fields.");
    }

    @Test
    @DisplayName("UI Responsiveness - Should display Register Ride page correctly on mobile size")
    void shouldDisplayRegisterRidePageCorrectlyOnMobile() {
        // Define um tamanho de tela de mobile (exemplo: iPhone X)
        driver.manage().window().setSize(new Dimension(375, 812));

        RegisterRidePage registerRidePage = new RegisterRidePage(driver);

        // Verifica se o campo start address está visível
        assertThat(registerRidePage.isStartAddressFieldVisible()).isTrue();
    }
}
