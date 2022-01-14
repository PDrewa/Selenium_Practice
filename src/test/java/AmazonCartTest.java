import io.qameta.allure.Step;
import org.junit.Assert;
import org.testng.annotations.Test;

public class AmazonCartTest extends BaseTest {

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
        checkPricesWithoutChangingQuantity(singleItemPrice, summaryItemPrice);
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
        checkPricesWithoutChangingQuantity(singleItemPrice, subTotalPrice);
    }

    @Test
    public void checkIncreaseQuantityOfProductInCart() {
        homePage.open().acceptCookiesIfPopupPresent().searchByProductName(propertyManager.getProperty("product.name"));
        searchResultsPage.selectSpecificProduct();
        String singleItemPrice = productDetailsPage.getSingleItemPrice();
        productDetailsPage.waitForPageToLoad().addProductToCart().selectCart();
        cartPage.waitForPageToLoad().selectDropdown().waitForDropdownToLoad().changeQuantityTo2().waitForPriceToUpdate();
        String subTotalPrice = cartPage.getSubtotalPrice();
        checkPricesAfterChangingQuantity(singleItemPrice, subTotalPrice
                , Integer.parseInt(propertyManager.getProperty("product.quantity")));
    }

    @Test
    public void checkDeleteProductFromCart() {
        homePage.open().acceptCookiesIfPopupPresent().searchByProductName(propertyManager.getProperty("product.name"));
        searchResultsPage.selectCategoryByPosition(propertyManager.getProperty("product.category.index")).selectSpecificProduct();
        productDetailsPage.addProductToCart();
        cartActionSummaryPage.waitForPageToLoad().viewCart();
        cartPage.waitForPageToLoad()
                .selectDropdown()
                .waitForDropdownToLoad()
                .changeQuantityTo2()
                .waitForPriceToUpdate()
                .deleteProductFromCart();
        checkCartIsEmpty();

    }


    @Step("Check if customer is on required page")
    private void checkCurrentPageTitle(String title) {
        logger.info("Checking that customer is redirected to proper page after clicking link");
        org.junit.Assert.assertEquals(title, driver.getTitle());
    }

    @Step("Check if prices are the same")
    private void checkPricesWithoutChangingQuantity(String firstPrice, String secondPrice) {
        logger.info("Checking that two prices in different locations are the same");
        org.junit.Assert.assertEquals(firstPrice, secondPrice);
    }

    @Step("Check if prices are calculated correctly after changing quantity")
    private void checkPricesAfterChangingQuantity(String firstPrice, String priceAfterChangingQuantity, int quantity) {
        logger.info("Checking that prices are calculated correctly after changing quantity of products");
        Double expectedPrice = Double.parseDouble(firstPrice) * (quantity);
        org.junit.Assert.assertEquals(expectedPrice, Double.valueOf(priceAfterChangingQuantity));
    }

    @Step("Check if cart is empty")
    private void checkCartIsEmpty() {
        logger.info("Checking that cart is empty");
        Assert.assertNotNull(cartPage.cartEmpty);
    }

}
