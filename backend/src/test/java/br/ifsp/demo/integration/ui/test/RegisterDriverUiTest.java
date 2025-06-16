package br.ifsp.demo.integration.ui.test;

import static org.assertj.core.api.Assertions.assertThat;

import br.ifsp.demo.integration.ui.BaseSeleniumTest;
import br.ifsp.demo.integration.ui.page.RegisterDriverPage;
import br.ifsp.demo.integration.ui.util.FakeDataFactory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@Tag("UiTest")
public class RegisterDriverUiTest extends BaseSeleniumTest {

    @Override
    protected void setInitialPage() {
        new RegisterDriverPage(driver);
    }

    @Test
    @DisplayName("Happy Path - Should register driver with valid data")
    void shouldRegisterDriverWithValidData() {
        RegisterDriverPage registerDriverPage = new RegisterDriverPage(driver);

        String name = FakeDataFactory.randomName();
        String lastname = FakeDataFactory.randomLastName();
        String email = FakeDataFactory.randomEmail();
        String cpf = FakeDataFactory.generateValidCpfFromList();
        String birthDate = FakeDataFactory.randomBirthDate();
        String password = FakeDataFactory.strongPassword();

        registerDriverPage.fillName(name);
        registerDriverPage.fillLastname(lastname);
        registerDriverPage.fillEmail(email);
        registerDriverPage.fillCpf(cpf);
        registerDriverPage.fillBirthDate(birthDate);
        registerDriverPage.fillPassword(password);
        registerDriverPage.fillConfirmPassword(password);

        registerDriverPage.submitForm();

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
        assertThat(driver.findElement(By.name("name")).isDisplayed()).isTrue();
    }

    @Test
    @DisplayName("UI Responsiveness - Should display Register Driver page correctly on mobile size")
    void shouldDisplayRegisterDriverPageCorrectlyOnMobile() {
        driver.manage().window().setSize(new Dimension(375, 812));
        RegisterDriverPage registerDriverPage = new RegisterDriverPage(driver);
        assertThat(registerDriverPage.isNameFieldVisible()).isTrue();
    }
}
