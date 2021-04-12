package com.pdrewa.pageobject.amazon;

import com.pdrewa.PropertyManager;
import com.pdrewa.pageobject.BasePage;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CartPage extends BasePage {

    private static final Logger logger = LogManager.getLogger(HomePage.class.getSimpleName());
    @FindBy(xpath = "//span[@class='a-size-medium a-color-base sc-price sc-white-space-nowrap']")
    WebElement totalPriceInCart;

    public CartPage(WebDriver driver, PropertyManager propertyManager) {
        super(driver, propertyManager);
    }

    @Step("Get subtotal cart price")
    public String getSubtotalPrice() {
        logger.info("Trying to get price at cart action summary page");
        return super.getPriceWithoutCurrency(totalPriceInCart);
    }

    @Step("Waiting for page to load")
    public CartPage waitForPageToLoad() {
        logger.info("Waiting for Cart page to load");
        super.waitForPageToLoad(totalPriceInCart);
        return this;
    }

}
