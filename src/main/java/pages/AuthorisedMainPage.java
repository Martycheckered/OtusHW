package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

public class AuthorisedMainPage extends AbstractPage {
    private static final String AVATAR_ICON = "//div[contains(@class, \"ic-blog-default-avatar\")]" ;
    private static final String MY_ACCOUNT_BUTTON = "//b[contains(@class, \"dropdown-text_name\")]" ;

    public AuthorisedMainPage(WebDriver driver) {
        super(driver);
    }

    public MyProfilePage goToMyProfile () {
        Actions builder = new Actions(driver);
        builder
                .moveToElement(driver.findElement(By.xpath(AVATAR_ICON)))
                .click(driver.findElement(By.xpath(MY_ACCOUNT_BUTTON)));
        Action mouseoverAndClick = builder.build();
        mouseoverAndClick.perform();
        logger.info("Transfer to \"My profile\"");

        return new MyProfilePage(driver);
    }


}
