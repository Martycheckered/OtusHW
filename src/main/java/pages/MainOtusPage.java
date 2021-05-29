package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MainOtusPage extends AbstractPage {
    private static final String URL = "https://otus.ru";
    private static final String LOGIN_BUTTON = "button[data-modal-id=\"new-log-reg\"]";

    public MainOtusPage(WebDriver driver) {
        super(driver);
    }

    public MainOtusPage open() {
        driver.get(URL);
        logger.info("Otus page is open");

        return this;
    }

    public LoginPageOtus goToLoginPage () {
        driver.findElement(By.cssSelector(LOGIN_BUTTON)).click();
        logger.info("Transfer to authorisation page");
        return new LoginPageOtus(driver);
    }

}
