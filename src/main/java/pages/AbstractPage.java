package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public abstract class AbstractPage {
    protected final Logger logger = LogManager.getLogger(AbstractPage.class);
    protected WebDriver driver;

    public AbstractPage(WebDriver driver) {
        this.driver = driver;
    }

    protected void clearAndFillTheFieldByCss(String selector, String value) {
        driver.findElement(By.cssSelector(selector)).clear();
        driver.findElement(By.cssSelector(selector)).sendKeys(value);
    }
    protected void clearAndFillTheFieldByXpath(String locator, String value) {
        driver.findElement(By.xpath(locator)).clear();
        driver.findElement(By.xpath(locator)).sendKeys(value);
    }

}
