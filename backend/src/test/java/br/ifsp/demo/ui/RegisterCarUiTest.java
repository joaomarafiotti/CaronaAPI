package br.ifsp.demo.ui;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Dimension;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("UiTest")
public class RegisterCarUiTest extends BaseSeleniumTest {

    @Override
    protected void setInitialPage() {
        new RegisterCarPage(driver); // Começa sempre na página de cadastro de carro
    }

    @Test
    @DisplayName("Happy Path - Should register car with valid data")
    void shouldRegisterCarWithValidData() {
        RegisterCarPage registerCarPage = new RegisterCarPage(driver);
        registerCarPage.fillBrand("Toyota");
        registerCarPage.fillModel("Corolla");
        registerCarPage.fillColor("Preto");
        registerCarPage.fillSeats("5");
        registerCarPage.fillLicensePlate("ABC-1234");

        registerCarPage.submitForm();

        // Esperado: redireciona para view de carros
        assertThat(driver.getCurrentUrl()).contains("/dashboard/driver/cars/view");
    }

    @Test
    @DisplayName("Sad Path - Should show error when submitting empty form")
    void shouldShowErrorWhenSubmittingEmptyForm() {
        RegisterCarPage registerCarPage = new RegisterCarPage(driver);

        registerCarPage.submitForm();

        // Deve exibir pelo menos a form-error geral
        assertThat(registerCarPage.isGeneralFormErrorVisible()).isTrue();
    }

    @Test
    @DisplayName("UI Responsiveness - Should display Register Car page correctly on mobile size")
    void shouldDisplayRegisterCarPageCorrectlyOnMobile() {
        // Define um tamanho de tela de mobile (exemplo: iPhone X)
        driver.manage().window().setSize(new Dimension(375, 812));

        RegisterCarPage registerCarPage = new RegisterCarPage(driver);

        // Verifica se o campo brand está visível
        assertThat(registerCarPage.isBrandFieldVisible()).isTrue();
    }
}
