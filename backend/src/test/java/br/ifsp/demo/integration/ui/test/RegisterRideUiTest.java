package br.ifsp.demo.integration.ui.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Dimension;

import br.ifsp.demo.integration.ui.BaseDriverTest;
import br.ifsp.demo.integration.ui.page.RegisterRidePage;
import br.ifsp.demo.integration.ui.util.FakeDataFactory;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("UiTest")
public class RegisterRideUiTest extends BaseDriverTest {

    @Override
    protected void setInitialPage() {
        new RegisterRidePage(driver);
    }

    @Test
    @DisplayName("Happy Path - Should register ride with valid data")
    void shouldRegisterRideWithValidData() {
        RegisterRidePage registerRidePage = new RegisterRidePage(driver);

        String startAddress = FakeDataFactory.randomAddress();
        String endAddress = FakeDataFactory.randomAddress();

        String futureDateTime = java.time.LocalDateTime.now()
            .plusDays(1).withHour(10).withMinute(0)
            .toString().replace("T", " ");
        String formattedFutureDateTime = futureDateTime.replace(" ", "T").substring(0, 16);

        registerRidePage.fillStartAddress(startAddress);
        registerRidePage.fillEndAddress(endAddress);
        registerRidePage.fillDepartureTime(formattedFutureDateTime);
        registerRidePage.selectCar("1");

        registerRidePage.submitForm();

        assertThat(registerRidePage.isFormSuccessVisible()).isTrue();
        assertThat(registerRidePage.getFormSuccessText()).contains("Ride registered successfully!");
    }

    @Test
    @DisplayName("Sad Path - Should show error when submitting empty form")
    void shouldShowErrorWhenSubmittingEmptyForm() {
        RegisterRidePage registerRidePage = new RegisterRidePage(driver);
        registerRidePage.submitForm();

        assertThat(registerRidePage.isFormErrorVisible()).isTrue();
        assertThat(registerRidePage.getFormErrorText()).contains("Please fill in all fields.");
    }

    @Test
    @DisplayName("UI Responsiveness - Should display Register Ride page correctly on mobile size")
    void shouldDisplayRegisterRidePageCorrectlyOnMobile() {
        driver.manage().window().setSize(new Dimension(375, 812));
        RegisterRidePage registerRidePage = new RegisterRidePage(driver);
        assertThat(registerRidePage.isStartAddressFieldVisible()).isTrue();
    }
}
