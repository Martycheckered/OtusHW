import io.github.bonigarcia.wdm.WebDriverManager;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class YandexTest {

    private Logger log = LogManager.getLogger(YandexTest.class);
    protected static WebDriver driver;
    private TestConfig testConfig = ConfigFactory.create(TestConfig.class);

    @Before
    public void startUp() {
        log.info("Let's test Yandex Market");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        log.info("Драйвер поднят");
    }

    @Test
    public void yandexMarketTest() {
        final String ELECTRONICA_BUTTON = "//span[text() = 'Электроника']";
        final String SMARTPHONES_BUTTON = "//a[normalize-space(text()) = 'Смартфоны']";
        final String BRAND_CONTAINER = "//fieldset[@data-autotest-id = '7893318']";
        final String SAMSUNG_CHECKBOX = "//span[text() = 'Samsung']";
        final String XIAOMI_CHECKBOX = "//span[text() = 'Xiaomi']";
        final String PRICE_SORT_BUTTON = "//button[text() = 'по цене']";
        final String LOADER = "div[data-tid='8bc8e36b']";
        final String FIRST_RESULT = "//div[@data-zone-name='SearchResults']//article[@data-autotest-id][1]";
        final String FIRST_RESULT_TITLE = "//h3[@data-zone-name = 'title']/a";

        final String ITEM_ADD_TO_COMPARISON_BUTTON = "//div[contains(@aria-label,'сравнению')]/div";
        final String POPUP_LOCATOR = "//div[@data-apiary-widget-id ='/content/popupInformer']//div[text() ='Товар %s добавлен к сравнению']";
        final String POPUP_TEXT_BLOCK = "input#header-search";
        final String COMPARE_BUTTON = "//a[@href='/my/compare-lists']/parent::div";
        final String COMPARED_ITEM_IMAGE = "//div[@data-tid='a86a07a1 2d4d9fc1']";


        driver.get(testConfig.market());
        log.info("Сайт яндекс маркета открыт");
        log.info("Проведём тестирование маркета и перейдем на электронику");
        driver.findElement(By.xpath(ELECTRONICA_BUTTON)).click();

        log.info("Перейдем на смартфоны");
        driver.findElement(By.xpath(SMARTPHONES_BUTTON)).click();
        // найдем контейнер с брендами, в нем кликнем чекбоксы Самсунг и Сяоми
        WebElement brandsFilter = driver.findElement(By.xpath(BRAND_CONTAINER));

        log.info("Поищем Самсунг и Сяоми, цена по возрастающей");
        brandsFilter.findElement(By.xpath(SAMSUNG_CHECKBOX)).click();
        brandsFilter.findElement(By.xpath(XIAOMI_CHECKBOX)).click();
        driver.findElement(By.xpath(PRICE_SORT_BUTTON)).click();

        log.info("Подождём исчезновения лоадера. Затем найдем телефоны и проверим плашку");

        new WebDriverWait(driver, 3).until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(LOADER))));
        WebElement firstArticle = driver.findElement(By.xpath(FIRST_RESULT));
        String articleTitle = firstArticle.findElement(By.xpath(FIRST_RESULT_TITLE)).getAttribute("title");

        WebElement addToComparisonButton = firstArticle.findElement(By.xpath(ITEM_ADD_TO_COMPARISON_BUTTON));
        addToComparisonButton.click();

        // подождём плашку

        WebElement comparisonPopup = new WebDriverWait(driver, 4).until(
                ExpectedConditions.visibilityOf(
                        driver.findElement(By.xpath(String.format(POPUP_LOCATOR, articleTitle)))));
        assertEquals("Поп-ап должен отображаться", true, comparisonPopup.isDisplayed());

        log.info("Ищем второй телефон");
        String anotherBrand = articleTitle.contains("Samsung") ? "Xiaomi" : "Samsung";
        WebElement secondArticle = driver.findElement(By.xpath("//div[@data-zone-name='SearchResults']//article//a[contains(@title, '"+ anotherBrand +"')]"));
        // нажмём на кнопку для сравнения по второму телефону
        driver.findElement(By.xpath("//div[@data-zone-name='SearchResults']//article//a[contains(@title, '"+ anotherBrand +"')]//ancestor::article//div[contains(@aria-label,'сравнению')]/div"))
                .click();
        articleTitle = secondArticle.getAttribute("title");
        comparisonPopup = new WebDriverWait(driver, 2).until(
                ExpectedConditions.visibilityOf(
                        driver.findElement(By.xpath(String.format(POPUP_LOCATOR, articleTitle)))));
        assertEquals("Поп-ап должен отображаться", true, comparisonPopup.isDisplayed());

        log.info("Перейдем в сравнение");
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(By.cssSelector(POPUP_TEXT_BLOCK))).build().perform();
        comparisonPopup.findElement(By.xpath(COMPARE_BUTTON)).click();

        log.info("Посмотрим, что в сравнении находятся 2 телефона");
        assertEquals(2,driver.findElements(By.xpath(COMPARED_ITEM_IMAGE)).size());
    }


    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        log.info("Драйвер закрыт");
    }


}