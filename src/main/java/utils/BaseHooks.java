package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

public class BaseHooks {
    protected static WebDriver driver;
    protected static Logger logger = LogManager.getLogger(BaseHooks.class);
    @BeforeClass
    public static void setup() {
        driver = WebDriverFactory.createDriver(Browsers.CHROME);

        if (driver != null) {
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.manage().deleteAllCookies();
            driver.manage().window().maximize();
        }
        logger.info("Driver is up");
    }

    @AfterClass
    public static void teardown() {
        if (driver != null) {
            driver.quit();
        }
        logger.info("Driver down");
    }



}

