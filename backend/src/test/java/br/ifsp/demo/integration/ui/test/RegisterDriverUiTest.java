package br.ifsp.demo.integration.ui.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Dimension;

import br.ifsp.demo.integration.ui.BaseSeleniumTest;
import br.ifsp.demo.integration.ui.page.RegisterDriverPage;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("UiTest")
public class RegisterDriverUiTest extends BaseSeleniumTest {

    @Override
    protected void setInitialPage() {
        new RegisterDriverPage(driver); // Começa sempre na página de cadastro de motorista
    }

    @Test
    @DisplayName("Happy Path - Should register driver with valid data")
    void shouldRegisterDriverWithValidData() {
        RegisterDriverPage registerDriverPage = new RegisterDriverPage(driver);
        registerDriverPage.fillName("João");
        registerDriverPage.fillLastname("Pedro");
        registerDriverPage.fillEmail("joao.pedro@example.com"); // email que não esta cadastrado
        registerDriverPage.fillCpf("123.456.789-00");
        registerDriverPage.fillBirthDate("1990-01-01");
        registerDriverPage.fillPassword("SenhaForte123!");
        registerDriverPage.fillConfirmPassword("SenhaForte123!");

        registerDriverPage.submitForm();

        // Esperado: redireciona para login
        assertThat(driver.getCurrentUrl()).contains("/login");
    }

    @Test
    @DisplayName("Sad Path - Should stay on register-driver page with empty form")
    void shouldStayOnRegisterDriverPageWithEmptyForm() {
        RegisterDriverPage registerDriverPage = new RegisterDriverPage(driver);

        registerDriverPage.submitForm();

        // Deve continuar na página de cadastro
        assertThat(driver.getCurrentUrl()).endsWith("/register-driver");
        assertThat(driver.getTitle()).isEqualTo("Cadastro de Motorista"); // Garantia extra
    }

    @Test
    @DisplayName("UI Responsiveness - Should display Register Driver page correctly on mobile size")
    void shouldDisplayRegisterDriverPageCorrectlyOnMobile() {
        // Define um tamanho de tela de mobile (exemplo: iPhone X)
        driver.manage().window().setSize(new Dimension(375, 812));

        RegisterDriverPage registerDriverPage = new RegisterDriverPage(driver);

        // Verifica se o campo name está visível
        assertThat(registerDriverPage.isNameFieldVisible()).isTrue();
    }
}
