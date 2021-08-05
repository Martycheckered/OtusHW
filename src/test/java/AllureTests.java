import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import static java.util.concurrent.TimeUnit.SECONDS;

public class AllureTests {
    private Logger logger = LogManager.getLogger(AllureTests.class);
    protected static WebDriver driver;
    TestConfig cfg = ConfigFactory.create(TestConfig.class);

    @BeforeEach
    public void startUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        logger.info("Драйвер поднят");
    }

    @AfterEach
    public void End() {
        if (driver != null) {
            driver.quit();
        }
        logger.info("Драйвер закрыт");
    }
    @Attachment(value = "Page screenshot", type = "image/png")
    protected byte[] saveAllureScreenshot() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
    @Test
    @DisplayName("Проверка корректности адреса и заголовка страницы")
    @Step("Снимок экрана")
    public void test1 () {
        driver.get(cfg.otusUrl());
        logger.info("Сайт Отус открыт");

        final String contactsButtonLocator = ".header2_subheader-link[href=\"/contacts/\"]";
        final String addressElementLocator = "//*[.='Адрес']/following-sibling::div";

        driver.findElement(By.cssSelector(contactsButtonLocator)).click();
        String address = driver.findElement(By.xpath(addressElementLocator)).getText();
        logger.info("Текст адреса - "+ address);
        Assertions.assertEquals(cfg.otusAddress(), address);


        String title = driver.getTitle();
        logger.info("Заголовок страницы - "+ title);
        Assertions.assertEquals("Контакты | OTUS", title);
        saveAllureScreenshot();
    }

    @Test
    @DisplayName("Проверка работы поиска номеров Теле2")
    @Step("Снимок экрана")
    public void test2 () {
        driver.get(cfg.tele2());
        logger.info("Сайт Tele2 открыт");

        final String firstTelephoneNumberlocator = "div[data-cartridge-type=\"ShopNumberCatalog\"] span.phone-number span";
        final String searchFieldLocator = "#searchNumber";

        //Берем первый блок, содержащий телефонный номер
        final WebElement numberBlockBefore = driver.findElement(By.cssSelector(firstTelephoneNumberlocator));
        final String beforeText = numberBlockBefore.getText();
        logger.info("Первый номер : " + beforeText);

        driver.findElement(By.cssSelector(searchFieldLocator)).sendKeys("97");

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

        Assertions.assertNotEquals(number.indexOf("97"), -1);
        saveAllureScreenshot();
    }

    @Test
    @DisplayName("Проверка текста по программе курса")
    @Step("Снимок экрана")
    public void test3 () {
        driver.get(cfg.otusUrl());
        logger.info("Сайт Отус открыт");

        final String faqButtonSelector = "a[title=\"FAQ\"]";
        final String questionLocator = "//*[.='Где посмотреть программу интересующего курса?']";
        final String answerLocator = "//*[.='Где посмотреть программу интересующего курса?']/following-sibling::div";

        driver.findElement(By.cssSelector(faqButtonSelector)).click();
        driver.findElement(By.xpath(questionLocator)).click();
        String answer = driver.findElement(By.xpath(answerLocator)).getText();
        logger.info(answer);

        Assertions.assertEquals(cfg.answer(), answer);
        saveAllureScreenshot();

    }

    @Test
    @DisplayName("Тест запроса на подписку")
    @Step("Снимок экрана")
    public void test4 () {
        final String email = "fofot26697@yehudabx.com";
        final String emailFieldLocator = "input[type=\"email\"]";
        final String submitButtonLocator = "button[type=\"submit\"]";
        final String successSubscribeTextLocator = "p.subscribe-modal__success";

        driver.get(cfg.otusUrl());
        logger.info("Сайт Отус открыт");

        driver.manage().timeouts().implicitlyWait(3, SECONDS);

        driver.findElement(By.cssSelector(emailFieldLocator)).sendKeys(email);
        driver.findElement(By.cssSelector(submitButtonLocator)).click();
        logger.info("Запрос на подписку отправлен");
        String text = driver.findElement(By.cssSelector(successSubscribeTextLocator)).getText();

        Assertions.assertEquals(cfg.success(), text);
        saveAllureScreenshot();
    }
}
