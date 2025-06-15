package br.ifsp.demo.ui.test;

import br.ifsp.demo.ui.BaseSeleniumTest;
import br.ifsp.demo.ui.page.ViewSolicitationNotificationsPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Dimension;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("UiTest")
public class ViewSolicitationNotificationsUiTest extends BaseSeleniumTest {

    @Override
    protected void setInitialPage() {
        new ViewSolicitationNotificationsPage(driver);
    }

    @Test
    @DisplayName("Happy Path - Should display solicitation notifications and title")
    void shouldDisplaySolicitationNotificationsAndTitle() {
        ViewSolicitationNotificationsPage page = new ViewSolicitationNotificationsPage(driver);
        assertThat(page.isTitleVisible()).isTrue();
        assertThat(page.hasSolicitations()).isTrue();
    }

    @Test
    @DisplayName("UI Responsiveness - Should show title on mobile layout")
    void shouldRenderOnMobile() {
        driver.manage().window().setSize(new Dimension(375, 812));
        ViewSolicitationNotificationsPage page = new ViewSolicitationNotificationsPage(driver);
        assertThat(page.isTitleVisible()).isTrue();
    }
}
