package br.ifsp.demo.integration.ui.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Dimension;

import br.ifsp.demo.integration.ui.BasePassengerTest;
import br.ifsp.demo.integration.ui.page.PassengerRidesPage;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("UiTest")
public class PassengerRidesUiTest extends BasePassengerTest {

    @Override
    protected void setInitialPage() {
        new PassengerRidesPage(driver);
    }

    @Test
    @DisplayName("Happy Path - Should display passenger rides and title")
    void shouldDisplayPassengerRidesAndTitle() {
        PassengerRidesPage page = new PassengerRidesPage(driver);
        assertThat(page.isTitleVisible()).isTrue();
        assertThat(page.hasRides()).isTrue();
    }

    @Test
    @DisplayName("UI Responsiveness - Should show title on mobile layout")
    void shouldRenderOnMobile() {
        driver.manage().window().setSize(new Dimension(375, 812));
        PassengerRidesPage page = new PassengerRidesPage(driver);
        assertThat(page.isTitleVisible()).isTrue();
    }
}