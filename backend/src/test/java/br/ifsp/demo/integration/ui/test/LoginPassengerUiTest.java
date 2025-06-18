package br.ifsp.demo.integration.ui.test;

import br.ifsp.demo.integration.ui.BaseSeleniumTest;
import br.ifsp.demo.integration.ui.page.LoginPage;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Dimension;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;


@Tag("UiTest")
public class LoginPassengerUiTest extends BaseSeleniumTest {

    @Override
    protected void setInitialPage() {
        new LoginPage(driver); // Garante que sempre começa na página de login
    }

    @Test
    @DisplayName("Happy Path - Should login with valid Passenger credentials")
    void shouldLoginWithValidPassengerCredentials() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.fillEmail("testuser@hotmail.com"); // substituir por um login válido
        loginPage.fillPassword("SenhaForte123!");
        loginPage.submitLogin();

        waitForUrlContains("/dashboard/passenger/profile");
        assertTrue(driver.getCurrentUrl().contains("/dashboard/passenger/profile"));
    }

    @Test
    @DisplayName("Sad Path - Should stay on login page with invalid credentials")
    void shouldStayOnLoginPageWithInvalidCredentials() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.fillEmail("invalid@example.com");
        loginPage.fillPassword("wrongpassword");
        loginPage.submitLogin();

        assertThat(driver.getCurrentUrl()).endsWith("/login");
        assertThat(loginPage.getPageTitle()).isEqualTo("Vite + React");
    }

    @Test
    @DisplayName("UI Responsiveness - Should display login page correctly on mobile size")
    void shouldDisplayLoginPageCorrectlyOnMobile() {
        // Define um tamanho de tela de mobile (exemplo: iPhone X)
        driver.manage().window().setSize(new Dimension(375, 812));

        LoginPage loginPage = new LoginPage(driver);

        // Verifica se o campo de email está visível (página renderizou corretamente)
        assertThat(loginPage.isEmailFieldVisible()).isTrue();
    }
}