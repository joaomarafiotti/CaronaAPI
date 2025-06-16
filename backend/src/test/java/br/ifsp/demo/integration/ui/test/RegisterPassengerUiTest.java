package br.ifsp.demo.integration.ui.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Dimension;

import br.ifsp.demo.integration.ui.BaseSeleniumTest;
import br.ifsp.demo.integration.ui.page.RegisterPassengerPage;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("UiTest")
public class RegisterPassengerUiTest extends BaseSeleniumTest {

    @Override
    protected void setInitialPage() {
        new RegisterPassengerPage(driver); // Começa sempre na página de cadastro de passageiro
    }

    @Test
    @DisplayName("Happy Path - Should register passenger with valid data")
    void shouldRegisterPassengerWithValidData() {
        RegisterPassengerPage registerPassengerPage = new RegisterPassengerPage(driver);
        registerPassengerPage.fillName("Maria");
        registerPassengerPage.fillLastname("Silva");
        registerPassengerPage.fillEmail("maria.silva@example.com"); // usar um e-mail que não esteja cadastrado
        registerPassengerPage.fillCpf("987.654.321-00");
        registerPassengerPage.fillBirthDate("1995-05-05");
        registerPassengerPage.fillPassword("SenhaForte123!");
        registerPassengerPage.fillConfirmPassword("SenhaForte123!");

        registerPassengerPage.submitForm();

        // Esperado: redireciona para login
        assertThat(driver.getCurrentUrl()).contains("/login");
    }

    @Test
    @DisplayName("Sad Path - Should stay on register-passenger page with empty form")
    void shouldStayOnRegisterPassengerPageWithEmptyForm() {
        RegisterPassengerPage registerPassengerPage = new RegisterPassengerPage(driver);

        registerPassengerPage.submitForm();

        // Deve continuar na página de cadastro
        assertThat(driver.getCurrentUrl()).endsWith("/register-passenger");
        assertThat(driver.getTitle()).isEqualTo("Cadastro de Passageiro"); // Garantia extra
    }

    @Test
    @DisplayName("UI Responsiveness - Should display Register Passenger page correctly on mobile size")
    void shouldDisplayRegisterPassengerPageCorrectlyOnMobile() {
        // Define um tamanho de tela de mobile (exemplo: iPhone X)
        driver.manage().window().setSize(new Dimension(375, 812));

        RegisterPassengerPage registerPassengerPage = new RegisterPassengerPage(driver);

        // Verifica se o campo name está visível
        assertThat(registerPassengerPage.isNameFieldVisible()).isTrue();
    }
}
