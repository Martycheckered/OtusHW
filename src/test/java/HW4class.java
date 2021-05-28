import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import pages.AuthorisedMainPage;
import pages.LoginPageOtus;
import pages.MainOtusPage;
import pages.MyProfilePage;
import pojo.Profile;
import utils.BaseHooks;

public class HW4class extends BaseHooks {
    private final Logger logger = LogManager.getLogger(HW4class.class);
    TestConfig cfg = ConfigFactory.create(TestConfig.class);

    /*@Before
    public void startUp() {
        //WebDriverManager.chromedriver().setup();
        driver = WebDriverFactory.createDriver(Browsers.CHROME);
        driver.manage().window().maximize();
        logger.info("Драйвер поднят");
    }
    @After
    public void End() {
        if (driver != null) {
            driver.quit();
        }
        logger.info("Драйвер закрыт");
    }*/

    @Test
    public void otusTest ()  {
        final String email = "fofot26697@yehudabx.com";
        final String pass = "abizzu4863$";
        Profile otusStudent = new Profile();
        otusStudent.setName("Иван");
        otusStudent.setSurname("Сидоров");
        otusStudent.setNameInLatin("Ivan");
        otusStudent.setSurnameInLatin("Sidorov");
        otusStudent.setBirthDate("12.10.1980");
        otusStudent.setFirstContactData("@MYSKYPE");
        otusStudent.setSecondContactData("@MYTelegram");

        MainOtusPage mainPage = new MainOtusPage(driver);
        LoginPageOtus loginPage = mainPage.open().goToLoginPage();
        AuthorisedMainPage authorisedMainPage = loginPage.logIn(email, pass);
        MyProfilePage myProfilePage = authorisedMainPage.goToMyProfile();
        myProfilePage.fillInThePersonalInfo(otusStudent);

        BaseHooks.teardown();
        BaseHooks.setup();

        MainOtusPage mainPage2 = new MainOtusPage(driver);
        LoginPageOtus loginPage2 = mainPage2.open().goToLoginPage();
        AuthorisedMainPage authorisedMainPage2 = loginPage2.logIn(email, pass);
        MyProfilePage myProfilePage2 = authorisedMainPage2.goToMyProfile();

        Assert.assertEquals(otusStudent.getName(),
                driver.findElement(By.cssSelector(MyProfilePage.NAME)).getAttribute("value"));
        Assert.assertEquals(otusStudent.getSurname(),
                driver.findElement(By.cssSelector(MyProfilePage.SURNAME)).getAttribute("value"));
        Assert.assertEquals(otusStudent.getNameInLatin(),
                driver.findElement(By.cssSelector(MyProfilePage.NAME_IN_LATIN)).getAttribute("value"));
        Assert.assertEquals(otusStudent.getSurnameInLatin(),
                driver.findElement(By.cssSelector(MyProfilePage.SURNAME_IN_LATIN)).getAttribute("value"));
        Assert.assertEquals(otusStudent.getName(),
                driver.findElement(By.cssSelector(MyProfilePage.NAME_IN_BLOG)).getAttribute("value"));
        Assert.assertEquals(otusStudent.getBirthDate(),
                driver.findElement(By.cssSelector(MyProfilePage.BIRTHDATE)).getAttribute("value"));

        Assert.assertEquals("Россия",
                driver.findElement(By.xpath(MyProfilePage.COUNTRY_LIST)).getText());
        Assert.assertEquals("Кемерово",
                driver.findElement(By.xpath(MyProfilePage.CITY_LIST)).getText());
        Assert.assertEquals("Выше среднего (Upper Intermediate)",
                driver.findElement(By.xpath(MyProfilePage.ENGLISH_LEVEL_LIST)).getText());

        Assert.assertTrue(driver.findElement(By.xpath(MyProfilePage.RADIOBUTTON_YES)).isSelected());
        Assert.assertTrue(driver.findElement(By.xpath(MyProfilePage.WORK_FORMAT_CHECKBOX)).isSelected());

        Assert.assertEquals("Skype",
                driver.findElement(By.xpath(MyProfilePage.FIRST_CONTACT_TYPE_LIST)).getText());
        Assert.assertEquals(otusStudent.getFirstContactData(),
                driver.findElement(By.xpath(MyProfilePage.FIRST_CONTACT_FIELD)).getAttribute("value"));
        Assert.assertEquals("Тelegram",
                driver.findElement(By.xpath(MyProfilePage.SECOND_CONTACT_TYPE_LIST)).getText());
        Assert.assertEquals(otusStudent.getSecondContactData(),
                driver.findElement(By.xpath(MyProfilePage.SECOND_CONTACT_FIELD)).getAttribute("value"));
    }

    @Test
    public void deb () {
        final String email = "fofot26697@yehudabx.com";
        final String pass = "abizzu4863$";
        MainOtusPage mainPage = new MainOtusPage(driver);
        LoginPageOtus loginPage = mainPage.open().goToLoginPage();
        AuthorisedMainPage authorisedMainPage = loginPage.logIn(email, pass);
        MyProfilePage myProfilePage = authorisedMainPage.goToMyProfile();

        logger.info(driver.findElement(By.xpath(MyProfilePage.RADIOBUTTON_YES)).isSelected());

        driver.findElement(By.xpath(MyProfilePage.RADIOBUTTON_YES)).click();
    }
}

