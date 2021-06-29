package otus.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Stepdefs  {
    WebDriver driver;
    private final Logger logger = LogManager.getLogger(Stepdefs.class);
    String beforeText;

    @Before
    public void startUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        logger.info("Драйвер поднят");
    }
    @After
    public void End() {
        if (driver != null) {
            driver.quit();
        }
        logger.info("Драйвер закрыт");
    }

    @Given("Otus site is open in browser")
    public void otusSiteIsOpenInBrowser() {
        final String otusUrl = "https://otus.ru";
        driver.get(otusUrl);
        logger.info("Сайт Отус открыт");
    }

    @When("Contacts button is clicked")
    public void contactsButtonIsClicked() {
        final String contactsButtonLocator = ".header2_subheader-link[href=\"/contacts/\"]";

        driver.findElement(By.cssSelector(contactsButtonLocator)).click();
    }

    @Then("Address information is correct")
    public void addressInformationIsCorrect() {
        final String addressElementLocator = "//*[.='Адрес']/following-sibling::div";
        final String otusAdress = "125167, г. Москва, Нарышкинская аллея., д. 5, стр. 2, тел. +7 499 938-92-02";

        String address = driver.findElement(By.xpath(addressElementLocator)).getText();
        logger.info("Текст адреса - "+ address);
        Assert.assertEquals(otusAdress, address);
    }


    @Then("Title name is correct")
    public void titleNameIsCorrect() {
        String title = driver.getTitle();
        logger.info("Заголовок страницы - "+ title);
        Assert.assertEquals("Контакты | OTUS", title);
    }
    @Given("Tele2 site is open in browser")
    public void teleSiteIsOpenInBrowser() {
        final String tele2Url = "https://msk.tele2.ru/shop/number";
        driver.get(tele2Url);
        logger.info("Сайт Теле2 открыт");
    }

    @When("User enters {string} in search field")
    public void userEntersInSearchField(String numberSnippet) {
        final String firstTelephoneNumberlocator = "div[data-cartridge-type=\"ShopNumberCatalog\"] span.phone-number span";
        final String searchFieldLocator = "#searchNumber";

        //Берем первый блок, содержащий телефонный номер
        final WebElement numberBlockBefore = driver.findElement(By.cssSelector(firstTelephoneNumberlocator));
        beforeText = numberBlockBefore.getText();
        logger.info("Первый номер : " + beforeText);

        driver.findElement(By.cssSelector(searchFieldLocator)).sendKeys(numberSnippet);
    }

    @Then("Output telephone number contains {string}")
    public void outputTelephoneNumberContains(String numberSnippet) {
        final String firstTelephoneNumberlocator = "div[data-cartridge-type=\"ShopNumberCatalog\"] span.phone-number span";

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


        String number =  after.getText().replace(" ","").replace("-","");
        logger.info(number);

        Assert.assertNotEquals(number.indexOf(numberSnippet), -1);
    }

}
