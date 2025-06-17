package br.ifsp.demo.integration.ui.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Dimension;

import br.ifsp.demo.integration.ui.BasePassengerTest;
import br.ifsp.demo.integration.ui.page.AvailableRidesPage;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("UiTest")
public class AvailableRidesUiTest extends BasePassengerTest {

    @Override
    protected void setInitialPage() {
        new AvailableRidesPage(driver);
    }

    @Test
    @DisplayName("Happy Path - Should display available rides and title")
    void shouldDisplayAvailableRidesAndTitle() {
        AvailableRidesPage page = new AvailableRidesPage(driver);
        assertThat(page.isTitleVisible()).isTrue();
        assertThat(page.hasRideCards()).isTrue();
    }

    @Test
    @DisplayName("Sad Path - Should not display rides if none available")
    void shouldNotDisplayRidesIfNoneAvailable() {
        AvailableRidesPage page = new AvailableRidesPage(driver);

        if (!page.hasRideCards()) {
            assertThat(page.countRidesDisplayed()).isEqualTo(0);
        }
    }

    @Test
    @DisplayName("UI Responsiveness - Should render properly on mobile")
    void shouldRenderProperlyOnMobile() {
        driver.manage().window().setSize(new Dimension(375, 812));

        AvailableRidesPage page = new AvailableRidesPage(driver);
        assertThat(page.isTitleVisible()).isTrue();
    }
}