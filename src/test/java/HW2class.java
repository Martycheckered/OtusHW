import io.github.bonigarcia.wdm.WebDriverManager;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static java.util.concurrent.TimeUnit.SECONDS;

public class HW2class {
    private Logger logger = LogManager.getLogger(HWClass.class);
    protected static WebDriver driver;
    TestConfig cfg = ConfigFactory.create(TestConfig.class);

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

    @Test
    public void test1 () {
        driver.get(cfg.otusUrl());
        logger.info("Сайт Отус открыт");

        driver.findElement(By.cssSelector(".header2_subheader-link[href=\"/contacts/\"]")).click();
        String address = driver.findElement(By.xpath("//*[.='Адрес']/following-sibling::div")).getText();
        logger.info("Текст адреса - "+ address);
        Assert.assertEquals(cfg.otusAddress(), address);

        driver.manage().window().maximize();
        String title = driver.getTitle();
        logger.info("Заголовок страницы - "+ title);
        Assert.assertEquals("Контакты | OTUS", title);
    }

    @Test
    public void test2 () {
        driver.get(cfg.tele2());
        logger.info("Сайт Tele2 открыт");

        final String firstTelephoneNumberlocator = "div[data-cartridge-type=\"ShopNumberCatalog\"] span.phone-number span";

        //Берем первый блок, содержащий телефонный номер
        final WebElement numberBlockBefore = driver.findElement(By.cssSelector(firstTelephoneNumberlocator));
        final String beforeText = numberBlockBefore.getText();
        logger.info("Первый номер : " + beforeText);

        driver.findElement(By.cssSelector("#searchNumber")).sendKeys("97");

        WebDriverWait wait = new WebDriverWait(driver, 5);

        //Критерий окончания ожидания: когда элемент (телефонный номер), находимый по одному и тому же локатору,
        // станет иметь другой текст
        boolean isNumberChanged = wait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                WebElement after = driver.findElement
                        (By.cssSelector(firstTelephoneNumberlocator));

                final String afterText = after.getText();
                return !afterText.equals(beforeText);
            }
        });
        logger.info("Номер после обновления изменился? : "+ isNumberChanged);
        WebElement after = driver.findElement
                (By.cssSelector(firstTelephoneNumberlocator));

        //В задании не было указано, какой проверкой должен завершиться тест, поэтому сделал свою
        //Проверка на то, что номер, очищенный от пробелов и тире, содержит "97", которое мы вбивали в поиск

        String number =  after.getText().replace(" ","").replace("-","");
        logger.info(number);

        Assert.assertNotEquals(number.indexOf("97"), -1);

    }

    @Test
    public void test3 () {
        driver.get(cfg.otusUrl());
        logger.info("Сайт Отус открыт");

        driver.findElement(By.cssSelector("a[title=\"FAQ\"]")).click();
        driver.findElement(By.xpath("//*[.='Где посмотреть программу интересующего курса?']")).click();
        String answer = driver.findElement(By.xpath("//*[.='Где посмотреть программу интересующего курса?']/following-sibling::div")).getText();
        logger.info(answer);

        Assert.assertEquals(cfg.answer(), answer);


    }

    @Test
    public void test4 () {
        final String email = "fofot26697@yehudabx.com";

        driver.get(cfg.otusUrl());
        logger.info("Сайт Отус открыт");

        driver.manage().timeouts().implicitlyWait(3, SECONDS);

        driver.findElement(By.cssSelector("input[type=\"email\"]")).sendKeys(email);
        driver.findElement(By.cssSelector("button[type=\"submit\"]")).click();
        logger.info("Запрос на подписку отправлен");
        String text = driver.findElement(By.cssSelector("p.subscribe-modal__success")).getText();

        Assert.assertEquals(cfg.success(), text);

    }
}
