package br.ifsp.demo.ui.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Dimension;

import br.ifsp.demo.ui.BaseSeleniumTest;
import br.ifsp.demo.ui.page.LoginPage;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("UiTest")
public class LoginUiTest extends BaseSeleniumTest {

    @Override
    protected void setInitialPage() {
        new LoginPage(driver); // Garante que sempre começa na página de login
    }

    @Test
    @DisplayName("Happy Path - Should login with valid driver credentials")
    void shouldLoginWithValidDriverCredentials() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.fillEmail("user@gmail.com"); // usuário válido do grupo
        loginPage.fillPassword("password");  // senha válida
        loginPage.submitLogin();

        // Espera um redirecionamento para a dashboard de driver
        assertThat(driver.getCurrentUrl()).contains("/dashboard/driver/profile");
    }

    @Test
    @DisplayName("Sad Path - Should stay on login page with invalid credentials")
    void shouldStayOnLoginPageWithInvalidCredentials() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.fillEmail("invalid@example.com");
        loginPage.fillPassword("wrongpassword");
        loginPage.submitLogin();

        // Deve continuar na página de login
        assertThat(driver.getCurrentUrl()).endsWith("/login");
        assertThat(driver.getTitle()).isEqualTo("Vite + React"); // Garantia extra
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
