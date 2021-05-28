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
        logger.info("Страница Отус открыта");

        return this;
    }

    public LoginPageOtus goToLoginPage () {
        driver.findElement(By.cssSelector(LOGIN_BUTTON)).click();
        logger.info("Переход на страницу авторизации");
        return new LoginPageOtus(driver);
    }

}
