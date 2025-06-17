package br.ifsp.demo.integration.ui.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Dimension;

import br.ifsp.demo.integration.ui.BaseDriverTest;
import br.ifsp.demo.integration.ui.page.RegisterCarPage;
import br.ifsp.demo.integration.ui.util.FakeDataFactory;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("UiTest")
public class RegisterCarUiTest extends BaseDriverTest {

    @Override
    protected void setInitialPage() {
        new RegisterCarPage(driver);
    }

    @Test
    @DisplayName("Happy Path - Should register car with valid data")
    void shouldRegisterCarWithValidData() {
        RegisterCarPage registerCarPage = new RegisterCarPage(driver);

        registerCarPage.fillBrand(FakeDataFactory.randomCarBrand());
        registerCarPage.fillModel(FakeDataFactory.randomCarModel());
        registerCarPage.fillColor(FakeDataFactory.randomColor());
        registerCarPage.fillSeats(String.valueOf(FakeDataFactory.randomSeats()));
        registerCarPage.fillLicensePlate(FakeDataFactory.randomPlate());

        registerCarPage.submitForm();

        assertThat(driver.getCurrentUrl()).contains("/dashboard/driver/cars/view");
    }

    @Test
    @DisplayName("Sad Path - Should show error when submitting empty form")
    void shouldShowErrorWhenSubmittingEmptyForm() {
        RegisterCarPage registerCarPage = new RegisterCarPage(driver);
        registerCarPage.submitForm();

        assertThat(registerCarPage.isGeneralFormErrorVisible()).isTrue();
    }

    @Test
    @DisplayName("UI Responsiveness - Should display Register Car page correctly on mobile size")
    void shouldDisplayRegisterCarPageCorrectlyOnMobile() {
        driver.manage().window().setSize(new Dimension(375, 812));

        RegisterCarPage registerCarPage = new RegisterCarPage(driver);
        assertThat(registerCarPage.isBrandFieldVisible()).isTrue();
    }
}