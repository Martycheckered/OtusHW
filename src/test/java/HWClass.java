import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class HWClass {
    private Logger logger = LogManager.getLogger(HWClass.class);
    protected static WebDriver driver;

    @Test
    public void siteTitleTest() {
        driver.get("https://otus.ru");
        String actualTitle = driver.getTitle();

        logger.info("Сайт открыт");
        logger.info("Заголовок сайта : "+ actualTitle);
        Assert.assertEquals("Онлайн‑курсы для профессионалов, дистанционное обучение современным профессиям",
           actualTitle);
    }

    @Before
    public void startUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        logger.info("Драйвер поднят");
    }

    @After
    public void End() {
        if (driver != null) {
            driver.quit();
        }
        logger.info("Драйвер закрыт");
    }

    /*@Test
    public void webDriverTest() {

        driver.get("https://otus.ru");
        logger.info("Сайт открыт");


    }*/
}
