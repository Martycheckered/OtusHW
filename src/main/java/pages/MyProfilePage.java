package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pojo.Profile;


public class MyProfilePage extends AbstractPage {
    public static final String NAME = "input[name=\"fname\"]";
    public static final String SURNAME = "input[name=\"lname\"]";
    public static final String NAME_IN_LATIN  = "input[name=\"fname_latin\"]" ;
    public static final String SURNAME_IN_LATIN = "input[name=\"lname_latin\"]" ;
    public static final String NAME_IN_BLOG = "input[name=\"blog_name\"]";
    public static final String BIRTHDATE = "input[name=\"date_of_birth\"]" ;
    public static final String COUNTRY_LIST = "//input[contains(@name,\"country\")]/following-sibling::div";
    public static final String COUNTRY_EXACT = "//button[contains(@title,\"Россия\")]";
    public static final String CITY_LIST = "//input[contains(@name,\"city\")]/following-sibling::div";
    public static final String CITY_EXACT = "//button[contains(@title,\"Кемерово\")]";
    public static final String ENGLISH_LEVEL_LIST = "//input[contains(@name,\"english_level\")]/following-sibling::div";
    public static final String ENGLISH_LEVEL_EXACT = "//button[contains(@title,\"Выше среднего\")]";
    public static final String RADIOBUTTON_YES = "//span[contains(@class,\"radio__label\") and contains(text(), 'Да')]";//input[contains(@type,\"radio\") and contains(@value, 'True')]";//span[contains(@class,\"radio__label\") and contains(text(), 'Да')]";
    public static final String WORK_FORMAT_CHECKBOX = "//input[contains(@title,\"Удаленно\")]";
    public static final String FIRST_CONTACT_TYPE_LIST = "//input[contains(@name,\"contact-0-service\")]/following-sibling::div";
    public static final String FIRST_CONTACT = "//input[contains(@name,\"contact-0-service\")]/../following-sibling::div//button[contains(@title,\"Skype\")]";
    public static final String FIRST_CONTACT_FIELD = "//input[contains(@name,\"contact-0-value\")]";
    public static final String ADD_CONTACT_BUTTON = "//button[contains(text(),\"Добавить\")]";
    public static final String SECOND_CONTACT_TYPE_LIST = "//input[contains(@name,\"contact-1-service\")]/following-sibling::div";
    public static final String SECOND_CONTACT = "//input[contains(@name,\"contact-1-service\")]/../following-sibling::div//button[contains(@title,\"Тelegram\")]";
    public static final String SECOND_CONTACT_FIELD = "//input[contains(@name,\"contact-1-value\")]";
    public static final String SAVE_AND_SKIP_BUTTON = "//button[contains(@title,\"Сохранить и заполнить позже\")]";



    public MyProfilePage(WebDriver driver) {
        super(driver);
    }

    public void fillInThePersonalInfo (Profile profile) {
        clearAndFillTheFieldByCss(NAME, profile.getName());
        clearAndFillTheFieldByCss(SURNAME, profile.getSurname());
        clearAndFillTheFieldByCss(NAME_IN_LATIN,profile.getNameInLatin());
        clearAndFillTheFieldByCss(SURNAME_IN_LATIN, profile.getSurnameInLatin());
        clearAndFillTheFieldByCss(NAME_IN_BLOG, profile.getName());
        clearAndFillTheFieldByCss(BIRTHDATE, profile.getBirthDate());

        /*driver.findElement(By.xpath(COUNTRY_LIST)).click();
        driver.findElement(By.xpath(COUNTRY_EXACT)).click();

        driver.findElement(By.xpath(CITY_LIST)).click();
        driver.findElement(By.xpath(CITY_EXACT)).click();

        driver.findElement(By.xpath(ENGLISH_LEVEL_LIST)).click();
        driver.findElement(By.xpath(ENGLISH_LEVEL_EXACT)).click();

        driver.findElement(By.xpath(RADIOBUTTON_YES)).click();

        WebElement checkbox = driver.findElement(By.xpath(WORK_FORMAT_CHECKBOX));
        if(!checkbox.isSelected()){
            checkbox.click();
        }*/

        driver.findElement(By.xpath(FIRST_CONTACT_TYPE_LIST)).click();
        driver.findElement(By.xpath(FIRST_CONTACT)).click();
        clearAndFillTheFieldByXpath(FIRST_CONTACT_FIELD, profile.getFirstContactData());


        driver.findElement(By.xpath(ADD_CONTACT_BUTTON)).click();

        driver.findElement(By.xpath(SECOND_CONTACT_TYPE_LIST)).click();
        driver.findElement(By.xpath(SECOND_CONTACT)).click();
        clearAndFillTheFieldByXpath(SECOND_CONTACT_FIELD, profile.getSecondContactData());


        driver.findElement(By.xpath(SAVE_AND_SKIP_BUTTON)).click();
        logger.info("Personal data is set");
    }


}
