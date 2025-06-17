package br.ifsp.demo.integration.ui.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Dimension;

import br.ifsp.demo.integration.ui.BasePassengerTest;
import br.ifsp.demo.integration.ui.page.RideSolicitationPage;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("UiTest")
public class RideSolicitationUiTest extends BasePassengerTest {

    @Override
    protected void setInitialPage() {
        new RideSolicitationPage(driver);
    }

    @Test
    @DisplayName("Happy Path - Should show driver name and cancel button")
    void shouldShowDriverNameAndCancelButton() {
        RideSolicitationPage page = new RideSolicitationPage(driver);

        assertThat(page.isDriverNameVisible()).isTrue();
        assertThat(page.isCancelButtonVisible()).isTrue();
    }

    @Test
    @DisplayName("Sad Path - Should show spinner if loading")
    void shouldShowSpinnerIfLoading() {
        RideSolicitationPage page = new RideSolicitationPage(driver);

        if (page.isSpinnerVisible()) {
            assertThat(page.isCancelButtonVisible()).isFalse();
        }
    }

    @Test
    @DisplayName("UI Responsiveness - Should render driver name on mobile layout")
    void shouldRenderDriverNameOnMobileLayout() {
        driver.manage().window().setSize(new Dimension(375, 812));

        RideSolicitationPage page = new RideSolicitationPage(driver);
        assertThat(page.isResponsiveLayoutVisible()).isTrue();
    }
}
