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
            driver.manage().window().maximize();
        }
        logger.info("Драйвер поднят");
    }

    @AfterClass
    public static void teardown() {
        if (driver != null) {
            driver.quit();
        }
        logger.info("Драйвер закрыт");
    }

    @After
    public void cleanUp() {
        driver.manage().deleteAllCookies();
    }

}

