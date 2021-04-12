package com.pdrewa.pageobject.amazon;

import com.pdrewa.PropertyManager;
import com.pdrewa.pageobject.BasePage;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CartActionSummaryPage extends BasePage {
    private static final Logger logger = LogManager.getLogger(ProductDetailsPage.class.getSimpleName());
    @FindBy(xpath = "//div[@class='a-row a-spacing-micro']//span[@class='a-color-price hlb-price a-inline-block a-text-bold']")
    WebElement price;

    @FindBy(xpath = "//a[@id='hlb-view-cart-announce']")
    WebElement viewCart;

    public CartActionSummaryPage(WebDriver driver, PropertyManager propertyManager) {
        super(driver, propertyManager);
    }

    @Step("Get price of products added to cart")
    public String getPriceWithoutCurrency() {
        logger.info("Trying to get price of products added to cart");
        return super.getPriceWithoutCurrency(price);
    }

    @Step("View Cart")
    public CartActionSummaryPage viewCart() {
        logger.info("Trying to get price at cart action summary page");
        viewCart.click();
        return this;
    }

    @Step("Waiting for page to load")
    public CartActionSummaryPage waitForPageToLoad() {
        logger.info("Waiting for Cart Action Summary page to load");
        super.waitForPageToLoad(viewCart);
        return this;
    }
}
