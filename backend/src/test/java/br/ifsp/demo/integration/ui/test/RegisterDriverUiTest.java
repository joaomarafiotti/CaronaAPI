package br.ifsp.demo.integration.ui.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import br.ifsp.demo.integration.ui.BaseSeleniumTest;
import br.ifsp.demo.integration.ui.page.RegisterDriverPage;

import java.time.Duration;

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
        registerDriverPage.fillEmail("joao.pedro@example.com"); // email que não está cadastrado
        registerDriverPage.fillCpf("390.533.447-05"); // CPF válido
        registerDriverPage.fillBirthDate("1990-01-01");
        registerDriverPage.fillPassword("SenhaForte123!");
        registerDriverPage.fillConfirmPassword("SenhaForte123!");

        registerDriverPage.submitForm();

        // Aguarda o redirecionamento para a página de login
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.urlContains("/login"));

        assertThat(driver.getCurrentUrl()).contains("/login");
    }

    @Test
    @DisplayName("Sad Path - Should stay on register-driver page with empty form")
    void shouldStayOnRegisterDriverPageWithEmptyForm() {
        RegisterDriverPage registerDriverPage = new RegisterDriverPage(driver);

        registerDriverPage.submitForm();

        assertThat(driver.getCurrentUrl()).endsWith("/register-driver");
        assertThat(driver.findElement(By.name("name")).isDisplayed()).isTrue(); // Garantia extra
    }

    @Test
    @DisplayName("UI Responsiveness - Should display Register Driver page correctly on mobile size")
    void shouldDisplayRegisterDriverPageCorrectlyOnMobile() {
        driver.manage().window().setSize(new Dimension(375, 812));

        RegisterDriverPage registerDriverPage = new RegisterDriverPage(driver);

        assertThat(registerDriverPage.isNameFieldVisible()).isTrue();
    }
}
