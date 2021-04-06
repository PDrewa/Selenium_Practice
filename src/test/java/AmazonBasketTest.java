import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class AmazonBasketTest extends BaseTest {

    static final String AMAZON_HOME_PAGE_URL = "https://www.amazon.com/";
    static final String AMAZON_HOME_PAGE_TITLE = "Amazon.com: Online Shopping for Electronics, Apparel, Computers, Books, DVDs & more";
    static final String ACCEPT_COOKIES_BUTTON_XPATH = "//input[@id='sp-cc-accept']";
    static final String SEARCH_FIELD_XPATH = "//input[@id='twotabsearchtextbox']";
    static final String SEARCH_BUTTON_XPATH = "//input[@id='nav-search-submit-button']";
    static final String SEARCH_RESULTS_BREADCRUMB_XPATH = "//*[contains(@class,'s-breadcrumb')]//span[@class='a-color-state a-text-bold']";
    static final String SEARCH_RESULTS_DEPARTMENTS_IN_LEFT_MENU_XPATH = "//div[@id='departments']//span[@class='a-size-base a-color-base']";
    static final int TARGET_DEPARTMENT_INDEX = 0;

    static final String SEARCH_SPECIFIC_ITEM_XPATH = "//*[contains(text(),'Certified Refurbished Kindle')]";
    static final String SEARCH_ADD_TO_CART_BUTTON_XPATH = "//input[@id='add-to-cart-button']";
    static final String SEARCH_PRICE_ON_ITEM_PAGE_XPATH = "//*[@id='price_inside_buybox']";
    static final String SEARCH_PRICE_ON_SUMMARY_PAGE_XPATH = "//div[@class='a-row a-spacing-micro']//span[@class='a-color-price hlb-price a-inline-block a-text-bold']";
    static final Integer POSITION = 2;
    static final String SEARCH_TERM = "Kindle";
    static final String EXPECTED_DEPARTMENT_NAME = "Kindle E-readers";
    static final String EXPECTED_SUBPAGE_TITLE = "Amazon.com: Certified Refurbished Kindle - Now with a Built-in Front Light - Black - Ad-Supported";
    static final String EXPECTED_CART_TITLE = "Amazon.com Shopping Cart";
    public static String SEARCH_CART_IS_EMPTY = "//div[@id='sc-active-cart']//*[contains(text(),'Your Amazon Cart is empty')]";

    private static void acceptCookiesIfPopupPresent() {
        try {
            driver.findElement(By.xpath(ACCEPT_COOKIES_BUTTON_XPATH)).click();
        } catch (NoSuchElementException e) {
            logger.info("Cookies pop-up is not present on page");
        }
    }

    private static boolean waitForTitleToBePresent(String title, long seconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(seconds))
                .until(ExpectedConditions.titleIs(title));
    }

    @Test
    public void basicBasketOperation() throws InterruptedException {
        driver.navigate().to(AMAZON_HOME_PAGE_URL);
        String pageTitle = driver.getTitle();
        Assert.assertEquals(pageTitle, AMAZON_HOME_PAGE_TITLE); //1st test
        acceptCookiesIfPopupPresent();
        driver.findElement(By.xpath(SEARCH_FIELD_XPATH)).sendKeys(SEARCH_TERM);
        driver.findElement(By.xpath(SEARCH_BUTTON_XPATH)).click();
        Assert.assertTrue(driver.findElement(By.xpath(SEARCH_RESULTS_BREADCRUMB_XPATH)).isDisplayed()); //2nd test
        Assert.assertEquals(driver.findElements(By.xpath(SEARCH_RESULTS_DEPARTMENTS_IN_LEFT_MENU_XPATH)).get(TARGET_DEPARTMENT_INDEX).getText(), EXPECTED_DEPARTMENT_NAME); //3rd test
        driver.findElements(By.xpath(SEARCH_RESULTS_DEPARTMENTS_IN_LEFT_MENU_XPATH)).get(TARGET_DEPARTMENT_INDEX).click();

        driver.findElement(By.xpath(SEARCH_SPECIFIC_ITEM_XPATH)).click();
        pageTitle = driver.getTitle();
        Assert.assertEquals(pageTitle, EXPECTED_SUBPAGE_TITLE); //4th

        String oneItemPrice = driver.findElement(By.xpath(SEARCH_PRICE_ON_ITEM_PAGE_XPATH)).getText().substring(1);
        driver.findElement(By.xpath(SEARCH_ADD_TO_CART_BUTTON_XPATH)).click();
        String summaryItemPrice = driver.findElement(By.xpath(SEARCH_PRICE_ON_SUMMARY_PAGE_XPATH)).getText().substring(1);
        Assert.assertEquals(oneItemPrice, summaryItemPrice);

        Thread.sleep(2000);
        driver.findElement(By.xpath("//a[@id='hlb-view-cart-announce']")).click();
        waitForTitleToBePresent(EXPECTED_CART_TITLE, 2);

        String subTotalPrice = driver.findElement(By.xpath("//span[@class='a-size-medium a-color-base sc-price sc-white-space-nowrap']")).getText().substring(1);
        softAssert.assertEquals(oneItemPrice, subTotalPrice); //5th

        Thread.sleep(2000);
        driver.findElement(By.xpath("//span[@class='a-button-text a-declarative']")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//div[@class='a-popover-inner']//ul//li[3]")).click();
        Thread.sleep(2000);

        subTotalPrice = driver.findElement(By.xpath("//span[@class='a-size-medium a-color-base sc-price sc-white-space-nowrap']")).getText().substring(1);
        Double twoItemPrice = Double.parseDouble(oneItemPrice) * 2;
        Assert.assertEquals(twoItemPrice, Double.valueOf(subTotalPrice)); //5th

        Thread.sleep(2000);
        driver.findElement(By.xpath("//input[@value='Delete']")).click();
        Thread.sleep(2000);

        Assert.assertNotNull(SEARCH_CART_IS_EMPTY);


        //div[@id='sc-active-cart']//*[contains(text(),'Your Amazon Cart is empty')]

    }

}
