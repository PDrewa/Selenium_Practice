import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class AmazonBasketTest extends BaseTest {

    public static final String SEARCH_DELETE_BUTTON_XPATH = "//input[@value='Delete']";
    static final String AMAZON_HOME_PAGE_URL = "https://www.amazon.com/";
    static final String AMAZON_HOME_PAGE_TITLE = "Amazon.com: Online Shopping for Electronics, Apparel, Computers, Books, DVDs & more";
    static final String ACCEPT_COOKIES_BUTTON_XPATH = "//input[@id='sp-cc-accept']";
    static final String SEARCH_FIELD_XPATH = "//input[@id='twotabsearchtextbox']";
    static final String SEARCH_BUTTON_XPATH = "//input[@id='nav-search-submit-button']";
    static final String SEARCH_RESULTS_BREADCRUMB_XPATH = "//*[contains(@class,'s-breadcrumb')]//span[@class='a-color-state a-text-bold']";
    static final String SEARCH_RESULTS_DEPARTMENTS_IN_LEFT_MENU_XPATH = "//div[@id='departments']//span[@class='a-size-base a-color-base']";
    static final int TARGET_DEPARTMENT_INDEX = 0;
    static final int TARGET_QUANTITY_INDEX = 3;
    static final String SEARCH_QUANTITY_XPATH = "//div[@class='a-popover-inner']//ul//li[" + TARGET_QUANTITY_INDEX + "]";
    static final String SEARCH_SPECIFIC_ITEM_XPATH = "//*[contains(text(),'Certified Refurbished Kindle')]";
    static final String SEARCH_ADD_TO_CART_BUTTON_XPATH = "//input[@id='add-to-cart-button']";
    static final String SEARCH_PRICE_ON_ITEM_PAGE_XPATH = "//*[@id='price_inside_buybox']";
    static final String SEARCH_PRICE_ON_SUMMARY_PAGE_XPATH = "//div[@class='a-row a-spacing-micro']//span[@class='a-color-price hlb-price a-inline-block a-text-bold']";
    static final String SEARCH_TERM = "Kindle";
    static final String EXPECTED_DEPARTMENT_NAME = "Kindle E-readers";
    static final String EXPECTED_SUBPAGE_TITLE = "Amazon.com: Certified Refurbished Kindle - Now with a Built-in Front Light - Black - Ad-Supported";
    static final String EXPECTED_CART_TITLE = "Amazon.com Shopping Cart";
    static final String SEARCH_VIEW_CART_XPATH = "//a[@id='hlb-view-cart-announce']";
    static final String SEARCH_CART_IS_EMPTY = "//div[@id='sc-active-cart']//*[contains(text(),'Your Amazon Cart is empty')]";
    static final String SEARCH_SUBTOTAL_PRICE = "//span[@class='a-size-medium a-color-base sc-price sc-white-space-nowrap']";
    static final String SEARCH_DROPDOWN_QUANTITY_XPATH = "//span[@class='a-button-text a-declarative']";

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
        Assert.assertEquals(pageTitle, AMAZON_HOME_PAGE_TITLE);
        acceptCookiesIfPopupPresent();

        driver.findElement(By.xpath(SEARCH_FIELD_XPATH)).sendKeys(SEARCH_TERM);
        driver.findElement(By.xpath(SEARCH_BUTTON_XPATH)).click();
        Assert.assertTrue(driver.findElement(By.xpath(SEARCH_RESULTS_BREADCRUMB_XPATH)).isDisplayed());
        Assert.assertEquals(driver.findElements(By.xpath(SEARCH_RESULTS_DEPARTMENTS_IN_LEFT_MENU_XPATH)).get(TARGET_DEPARTMENT_INDEX).getText(), EXPECTED_DEPARTMENT_NAME);

        driver.findElements(By.xpath(SEARCH_RESULTS_DEPARTMENTS_IN_LEFT_MENU_XPATH)).get(TARGET_DEPARTMENT_INDEX).click();

        driver.findElement(By.xpath(SEARCH_SPECIFIC_ITEM_XPATH)).click();

        pageTitle = driver.getTitle();
        Assert.assertEquals(pageTitle, EXPECTED_SUBPAGE_TITLE);

        String oneItemPrice = driver.findElement(By.xpath(SEARCH_PRICE_ON_ITEM_PAGE_XPATH)).getText().substring(1);
        driver.findElement(By.xpath(SEARCH_ADD_TO_CART_BUTTON_XPATH)).click();

        String summaryItemPrice = driver.findElement(By.xpath(SEARCH_PRICE_ON_SUMMARY_PAGE_XPATH)).getText().substring(1);
        Assert.assertEquals(oneItemPrice, summaryItemPrice);

        Thread.sleep(2000);
        driver.findElement(By.xpath(SEARCH_VIEW_CART_XPATH)).click();
        waitForTitleToBePresent(EXPECTED_CART_TITLE, 2);

        String subTotalPrice = driver.findElement(By.xpath(SEARCH_SUBTOTAL_PRICE)).getText().substring(1);
        softAssert.assertEquals(oneItemPrice, subTotalPrice);

        Thread.sleep(2000);
        driver.findElement(By.xpath(SEARCH_DROPDOWN_QUANTITY_XPATH)).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath(SEARCH_QUANTITY_XPATH)).click();
        Thread.sleep(2000);

        subTotalPrice = driver.findElement(By.xpath(SEARCH_SUBTOTAL_PRICE)).getText().substring(1);
        Double twoItemPrice = Double.parseDouble(oneItemPrice) * (TARGET_QUANTITY_INDEX - 1);
        Assert.assertEquals(twoItemPrice, Double.valueOf(subTotalPrice));

        Thread.sleep(2000);
        driver.findElement(By.xpath(SEARCH_DELETE_BUTTON_XPATH)).click();

        Thread.sleep(2000);
        Assert.assertNotNull(SEARCH_CART_IS_EMPTY);

    }

}
