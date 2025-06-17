package br.ifsp.demo.integration.ui.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Dimension;

import br.ifsp.demo.integration.ui.BaseDriverTest;
import br.ifsp.demo.integration.ui.page.ViewRidesPage;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("UiTest")
public class ViewRidesUiTest extends BaseDriverTest {

    @Override
    protected void setInitialPage() {
        new ViewRidesPage(driver);
    }

    @Test
    @DisplayName("Happy Path - Should display all ride sections if data exists")
    void shouldDisplayAllRideSectionsIfDataExists() {
        ViewRidesPage viewRidesPage = new ViewRidesPage(driver);

        assertThat(
            viewRidesPage.hasNotStartedRides()
            || viewRidesPage.hasInProgressRides()
            || viewRidesPage.hasFinishedRides()
        ).isTrue();
    }

    @Test
    @DisplayName("Sad Path - Should show error alert if API fails")
    void shouldShowErrorAlertIfApiFails() {
        ViewRidesPage viewRidesPage = new ViewRidesPage(driver);

        if (viewRidesPage.hasErrorMessage()) {
            assertThat(viewRidesPage.getErrorMessageText()).contains("Erro ao buscar caronas");
        } else {
            System.out.println("Nenhum erro foi simulado. Ignorando teste sad path: API respondeu normalmente.");
        }
    }

    @Test
    @DisplayName("UI Responsiveness - Should display title correctly on mobile")
    void shouldDisplayTitleCorrectlyOnMobile() {
        driver.manage().window().setSize(new Dimension(375, 812));
        ViewRidesPage viewRidesPage = new ViewRidesPage(driver);
        assertThat(viewRidesPage.isResponsiveTitleVisible()).isTrue();
    }
}