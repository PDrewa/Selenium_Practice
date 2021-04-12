import com.pdrewa.LogUtil;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;

public class AmazonBasketTest extends BaseTest {

    public static final String SEARCH_DELETE_BUTTON_XPATH = "//input[@value='Delete']";
    static final String AMAZON_HOME_PAGE_URL = "https://www.amazon.com/";//ok
    static final String ACCEPT_COOKIES_BUTTON_XPATH = "//input[@id='sp-cc-accept']";
    static final String SEARCH_FIELD_XPATH = "//input[@id='twotabsearchtextbox']";
    static final String SEARCH_RESULTS_DEPARTMENTS_IN_LEFT_MENU_XPATH = "//div[@id='departments']//span[@class='a-size-base a-color-base']";
    static final int TARGET_DEPARTMENT_INDEX = 1; //product.category
    static final int TARGET_QUANTITY_INDEX = 3; //product.quantity.index
    static final String SEARCH_QUANTITY_XPATH = "//div[@class='a-popover-inner']//ul//li[" + TARGET_QUANTITY_INDEX + "]";
    static final String SEARCH_SPECIFIC_ITEM_XPATH = "//*[contains(text(),'Certified Refurbished Kindle')]";
    static final String SEARCH_ADD_TO_CART_BUTTON_XPATH = "//input[@id='add-to-cart-button']";
    static final String SEARCH_PRICE_ON_ITEM_PAGE_XPATH = "//*[@id='price_inside_buybox']";
    static final String SEARCH_PRICE_ON_SUMMARY_PAGE_XPATH = "//div[@class='a-row a-spacing-micro']//span[@class='a-color-price hlb-price a-inline-block a-text-bold']";
    static final String SEARCH_TERM = "Kindle";//ok
    static final String EXPECTED_SUBPAGE_TITLE = "Amazon.com: Certified Refurbished Kindle - Now with a Built-in Front Light - Black - Ad-Supported";//ok
    static final String EXPECTED_CART_TITLE = "Amazon.com Shopping Cart";//ok
    static final String SEARCH_VIEW_CART_XPATH = "//a[@id='hlb-view-cart-announce']";
    static final String SEARCH_CART_IS_EMPTY = "//div[@id='sc-active-cart']//*[contains(text(),'Your Amazon Cart is empty')]";
    static final String SEARCH_SUBTOTAL_PRICE_XPATH = "//span[@class='a-size-medium a-color-base sc-price sc-white-space-nowrap']";
    static final String SEARCH_DROPDOWN_QUANTITY_XPATH = "//span[@class='a-button-text a-declarative']";
    static final String SEARCH_QUANTITY_INCREASE_XPATH = "//span[contains(text(), 'Subtotal') and contains(text(), '" + (TARGET_QUANTITY_INDEX - 1) + "')]";


    private static boolean waitForTitleToBePresent(String title, long seconds, long intervalSeconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(seconds), Duration.ofSeconds(intervalSeconds))
                .until(ExpectedConditions.titleIs(title));
    }

    private static WebElement waitForElementToBePresent(String xPath, long seconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(seconds))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath(xPath)));
    }

    private static WebElement waitForElementToBeClickable(String xPath, long seconds, long intervalSeconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(seconds), Duration.ofSeconds(intervalSeconds))
                .until(ExpectedConditions.elementToBeClickable(By.xpath(xPath)));
    }


    @Step("Open web page")
    private static void openWebPage(String url) {
        logger.info(AmazonBasketTest.class.getName() + " Navigating to website with url:   " + url);
        driver.navigate().to(url);
    }

    @Step("Enter text {1} into field with path {0}")
    private static void enterTextIntoField(String xpath, String text) {
        logger.info(AmazonBasketTest.class.getName() + "Entering text: \"" + text + "\" into field with XPath: " + xpath);
        Actions actions = new Actions(driver);
        actions.sendKeys(findElement(xpath), text).perform();
    }

    @Step("Press {0} key")
    private static void pressKey(Keys key) {
        logger.info(AmazonBasketTest.class.getName() + " Pressing key: " + key.name());
        Actions actions = new Actions(driver);
        actions.sendKeys(key).perform();
    }

    @Step("Click on element with xpath {0}")
    private static void clickOnElement(String xpath) {
        clickOnElement(xpath, -1);
    }

    @Step("Click on element from list with position {1}")
    private static void clickOnElement(String xpath, int position) {
        logger.info(AmazonBasketTest.class.getName() + " Clicking on elment with xpath: " + xpath);
        Actions actions = new Actions(driver);
        actions.click(findElement(xpath, position)).perform();
    }

    @Step("Click on web element from list with position {1}")
    private static void clickOnElement(WebElement webElement) {
        logger.info(AmazonBasketTest.class.getName() + " Clicking on web element: " + webElement);
        Actions actions = new Actions(driver);
        actions.click(webElement).perform();
    }

    private static WebElement findElement(String xpath) {
        return findElement(xpath, -1);
    }

    private static WebElement findElement(String xpath, int position) {
        WebElement element = null;
        try {
            if (position == -1)
                element = driver.findElement(By.xpath(xpath));
            else
                element = driver.findElements(By.xpath(xpath)).get(position - 1);
        } catch (Exception e) {
            logger.error(AmazonBasketTest.class.getName() + " Element with XPath: " + xpath + "  was not found on the page.");
            LogUtil.logStackTrace(e, logger);
        }
        return element;
    }

    @Test
    public void checkIncreasingProductQuantityInCart() {
        openWebPage(AMAZON_HOME_PAGE_URL);
        //acceptCookiesIfPopupPresent();
        enterTextIntoField(SEARCH_FIELD_XPATH, SEARCH_TERM);
        pressKey(Keys.ENTER);
        clickOnElement(SEARCH_RESULTS_DEPARTMENTS_IN_LEFT_MENU_XPATH, TARGET_DEPARTMENT_INDEX);
        clickOnElement(SEARCH_SPECIFIC_ITEM_XPATH);
        String oneItemPrice = getPriceWithoutCurrency(SEARCH_PRICE_ON_ITEM_PAGE_XPATH);
        clickOnElement(SEARCH_ADD_TO_CART_BUTTON_XPATH);
        clickOnElement(waitForElementToBePresent(SEARCH_VIEW_CART_XPATH, 2));
        waitForTitleToBePresent(EXPECTED_CART_TITLE, 2, 1);
        clickOnElement(waitForElementToBePresent(SEARCH_DROPDOWN_QUANTITY_XPATH, 3));
        clickOnElement(waitForElementToBeClickable(SEARCH_QUANTITY_XPATH, 2, 1));
        waitForElementToBePresent(SEARCH_QUANTITY_INCREASE_XPATH, 2);

        String subTotalPrice = getPriceWithoutCurrency(SEARCH_SUBTOTAL_PRICE_XPATH);
        checkPricesAfterChangingQuantity(oneItemPrice, subTotalPrice, TARGET_QUANTITY_INDEX - 1);
    }

    @Test
    public void checkRemovingProductsFromCart() {
        openWebPage(AMAZON_HOME_PAGE_URL);
        //acceptCookiesIfPopupPresent();
        enterTextIntoField(SEARCH_FIELD_XPATH, SEARCH_TERM);
        pressKey(Keys.ENTER);
        clickOnElement(SEARCH_RESULTS_DEPARTMENTS_IN_LEFT_MENU_XPATH, TARGET_DEPARTMENT_INDEX);
        clickOnElement(SEARCH_SPECIFIC_ITEM_XPATH);
        clickOnElement(SEARCH_ADD_TO_CART_BUTTON_XPATH);
        clickOnElement(waitForElementToBePresent(SEARCH_VIEW_CART_XPATH, 2));
        waitForTitleToBePresent(EXPECTED_CART_TITLE, 2, 1);
        clickOnElement(waitForElementToBePresent(SEARCH_DROPDOWN_QUANTITY_XPATH, 3));
        clickOnElement(waitForElementToBeClickable(SEARCH_QUANTITY_XPATH, 2, 1));
        waitForElementToBePresent(SEARCH_QUANTITY_INCREASE_XPATH, 2);

        clickOnElement(waitForElementToBePresent(SEARCH_DELETE_BUTTON_XPATH, 2));
        clickOnElement(waitForElementToBePresent(SEARCH_CART_IS_EMPTY, 2));
    }


    @Step("Check if prices are calculated correctly after changing quantity")
    private void checkPricesAfterChangingQuantity(String singleProductPrice, String multipleProductPrice, int quantity) {
        logger.info("Checking that prices are calculated correctly after changing quantity of products");
        Double expectedPrice = Double.parseDouble(singleProductPrice) * (quantity);
        org.junit.Assert.assertEquals(expectedPrice, Double.valueOf(multipleProductPrice));
    }

    @Step("Get price from xpath {0} without currency sign")
    private String getPriceWithoutCurrency(String xpath) {
        logger.info(AmazonBasketTest.class.getName() + "Getting price value without currency sign from xpath: " + xpath);
        return getTextFromXpath(xpath).substring(1);
    }

    private String getTextFromXpath(String xpath) {
        logger.info(AmazonBasketTest.class.getName() + "Getting text from xpath: " + xpath);
        return findElement(xpath).getText();
    }

    @Test
    public void checkChooseSpecificProductFromSearchList() {
        homePage.open().acceptCookiesIfPopupPresent().searchByProductName(propertyManager.getProperty("product.name"));
        searchResultsPage.selectCategoryByPosition(propertyManager.getProperty("product.category.index")).selectSpecificProduct();
        checkCurrentPageTitle(propertyManager.getProperty("expected.subpage.title"));
    }

    @Test
    public void checkAddingProductToCart() {
        homePage.open().acceptCookiesIfPopupPresent().searchByProductName(propertyManager.getProperty("product.name"));
        searchResultsPage.selectCategoryByPosition(propertyManager.getProperty("product.category.index")).selectSpecificProduct();
        String singleItemPrice = productDetailsPage.getSingleItemPrice();
        productDetailsPage.addProductToCart();
        String summaryItemPrice = cartActionSummaryPage.getPriceWithoutCurrency();
        checkPrices(singleItemPrice, summaryItemPrice);
    }

    @Test
    public void checkPriceInCartSameAsOnProductPage() {
        homePage.open().acceptCookiesIfPopupPresent().searchByProductName(propertyManager.getProperty("product.name"));
        searchResultsPage.selectCategoryByPosition(propertyManager.getProperty("product.category.index")).selectSpecificProduct();
        String singleItemPrice = productDetailsPage.getSingleItemPrice();
        productDetailsPage.addProductToCart();
        cartActionSummaryPage.waitForPageToLoad().viewCart();
        cartPage.waitForPageToLoad();
        String subTotalPrice = cartPage.getSubtotalPrice();
        checkPrices(singleItemPrice, subTotalPrice);
    }

    @Step("Check if customer is on required page")
    private void checkCurrentPageTitle(String title) {
        logger.info("Checking that customer is redirected to proper page after clicking link");
        org.junit.Assert.assertEquals(title, driver.getTitle());
    }

    @Step("Check if prices are the same")
    private void checkPrices(String firstPrice, String secondPrice) {
        logger.info("Checking that two prices in different locations are the same");
        org.junit.Assert.assertEquals(firstPrice, secondPrice);
    }

}
