import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AmazonBasketTest extends BaseTest {

    static final String AMAZON_HOME_PAGE_URL = "https://www.amazon.com/";
    static final String AMAZON_HOME_PAGE_TITLE = "Amazon.com: Online Shopping for Electronics, Apparel, Computers, Books, DVDs & more";
    static final String ACCEPT_COOKIES_BUTTON_XPATH = "//input[@id='sp-cc-accept']";



    @Test
    public void basicBasketOperation() {
        driver.navigate().to(AMAZON_HOME_PAGE_URL);
        String pageTitle = driver.getTitle();
        Assert.assertEquals(pageTitle, AMAZON_HOME_PAGE_TITLE); //1st test




    }

    private static void acceptCookiesIfPopupPresent(){
        try{
            driver.findElement(By.xpath(ACCEPT_COOKIES_BUTTON_XPATH)).click();
        } catch(NoSuchElementException e) {
            logger.info("Cookies pop-up is not present on page");
        }
    }


}
