package br.ifsp.demo.integration.ui.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Dimension;

import br.ifsp.demo.integration.ui.BaseSeleniumTest;
import br.ifsp.demo.integration.ui.page.PassengerRideRequestsPage;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("UiTest")
public class PassengerRideRequestsUiTest extends BaseSeleniumTest {

    @Override
    protected void setInitialPage() {
        new PassengerRideRequestsPage(driver);
    }

    @Test
    @DisplayName("Happy Path - Should display ride requests and title")
    void shouldDisplayRideRequestsAndTitle() {
        PassengerRideRequestsPage page = new PassengerRideRequestsPage(driver);
        assertThat(page.isTitleVisible()).isTrue();
        assertThat(page.hasSolicitations()).isTrue(); // exige ao menos uma solicitação no banco
    }

    @Test
    @DisplayName("UI Responsiveness - Should show title on mobile layout")
    void shouldRenderOnMobile() {
        driver.manage().window().setSize(new Dimension(375, 812));
        PassengerRideRequestsPage page = new PassengerRideRequestsPage(driver);
        assertThat(page.isTitleVisible()).isTrue();
    }
}
