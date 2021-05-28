package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPageOtus extends AbstractPage{

    private static final String EMAIL_FIELD = "input[placeholder=\"Электронная почта\"][type=\"text\"]";
    private static final String PASSWORD_FIELD = "input[placeholder=\"Введите пароль\"][type=\"password\"]";
    private static final String SUBMIT_BUTTON = "//button[normalize-space(text())='Войти']";

    public LoginPageOtus(WebDriver driver) {
        super(driver);
    }

    public AuthorisedMainPage logIn (String email, String password) {
        driver.findElement(By.cssSelector(EMAIL_FIELD)).sendKeys(email);
        driver.findElement(By.cssSelector(PASSWORD_FIELD)).sendKeys(password);
        driver.findElement(By.xpath(SUBMIT_BUTTON)).click();
        logger.info("Авторизация произведена");
        return new AuthorisedMainPage(driver);
    }




}
