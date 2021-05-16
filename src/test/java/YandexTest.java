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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class YandexTest {
    private final Logger logger = LogManager.getLogger(YandexTest.class);
    protected static WebDriver driver;
    TestConfig cfg = ConfigFactory.create(TestConfig.class);
    private WebDriverWait wait;

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 5);
        logger.info("Драйвер поднят");
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        logger.info("Драйвер закрыт");
    }

    @Test
    public void test() {
        final String ELECTRONICA_BUTTON = "a[href=\"/catalog--elektronika/54440\"]";
        final String SMARTPHONES_BUTTON = "ul[data-autotest-id=\"subItems\"] a[href^=\"/catalog--smartfony\"]";
        final String SAMSUNG_CHECKBOX = "//span[.='Samsung']/..";
        final String XIAOMI_CHECKBOX = "//span[.='Xiaomi']/..";
        final String PRICE_SORT_BUTTON = "button[data-autotest-id=\"dprice\"]";
        final String LOADER = "div[data-tid=\"8bc8e36b\"]";
        final String SMARTPHONES = "article[data-autotest-id=\"product-snippet\"]";
        final String ITEM_TITLE = "h3 span";
        final String ITEM_ADD_TO_COMPARE_BUTTON = "div[aria-label$=\"сравнению\"]";
        final String POPUP_TEXT = "//div[text()[contains(.,'Всего в списке')]]/preceding-sibling::div";
        final String COMPARE_BUTTON = "a[href=\"/my/compare-lists\"]";
        final String COMPARED_ITEM_IMAGE = "img[alt^=\"Смартфон\"]";

        driver.get(cfg.market());
        logger.info("Сайт Яндекс.Маркет открыт");

        driver.findElement(By.cssSelector(ELECTRONICA_BUTTON)).click();
        logger.info("Кнопка \"Электроника\" нажата");
        driver.findElement(By.cssSelector(SMARTPHONES_BUTTON)).click();
        logger.info("Кнопка \"Смартфоны\" нажата");

        driver.findElement(By.xpath(SAMSUNG_CHECKBOX)).click();
        logger.info("Чекбокс \"Samsung\" нажат");
        driver.findElement(By.xpath(XIAOMI_CHECKBOX)).click();
        logger.info("Чекбокс \"Xiaomi\" нажат");

        driver.findElement(By.cssSelector(PRICE_SORT_BUTTON)).click();
        logger.info("Список отсортирован по цене");

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(LOADER)));

        List<WebElement> listOfSmartphonesOnPage = driver.findElements(By.cssSelector(SMARTPHONES));

        boolean isSamsungFound = false;
        boolean isXiaomiFound = false;
        for (int i = 0; i < listOfSmartphonesOnPage.size() ; i++) {

            WebElement current = listOfSmartphonesOnPage.get(i);
            String productTitle = current.findElement(By.cssSelector(ITEM_TITLE)).getText();

            if( !isSamsungFound && productTitle.contains("Samsung")) {

                current.findElement(By.cssSelector(ITEM_ADD_TO_COMPARE_BUTTON)).click();
                isSamsungFound = true;
                logger.info("Смартфон Samsung добавлен к сравнению");

                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(POPUP_TEXT)));
                Assert.assertEquals("Товар "+ getTitleWithoutColor(productTitle) +" добавлен к сравнению",
                        driver.findElement(By.xpath(POPUP_TEXT)).getText());
                logger.info("Текст плашки Samsung корректен");
            }
            if( !isXiaomiFound && productTitle.contains("Xiaomi")) {

                current.findElement(By.cssSelector(ITEM_ADD_TO_COMPARE_BUTTON)).click();
                isXiaomiFound = true;
                logger.info("Смартфон Xiaomi добавлен к сравнению");

                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(POPUP_TEXT)));
                Assert.assertEquals("Товар "+ getTitleWithoutColor(productTitle) +" добавлен к сравнению",
                        driver.findElement(By.xpath(POPUP_TEXT)).getText());
                logger.info("Текст плашки Xiaomi корректен");
            }
            if (isSamsungFound && isXiaomiFound) {

                wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(COMPARE_BUTTON))).click();
                break;
            }
        }

        List<WebElement> listOfCompared = driver.findElements(By.cssSelector(COMPARED_ITEM_IMAGE));
        Assert.assertEquals(listOfCompared.size(), 2);
        logger.info("Количество товаров в списке для сравнения корректно");
    }


    //удаляем из названия смартфона информацию о цвете (цвет есть в названии товара в списке,
    // но его нет в названии товара в плашке после добавления к сравнению) - обрезаем строку после запятой
    public String getTitleWithoutColor (String title) {

        return title.substring(0, title.indexOf(','));
    }


    }
