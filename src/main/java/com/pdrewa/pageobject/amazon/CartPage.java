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
    public WebElement totalPriceInCart;

    @FindBy(xpath = "//span[@class='a-button-text a-declarative']")
    public WebElement dropdown;

    @FindBy(xpath = "//div[@class='a-popover-inner']//ul//li[3]")
    public WebElement quantityEquals2;

    @FindBy(xpath = "//span[contains(text(), 'Subtotal') and contains(text(), '2')]")
    public WebElement updatedPrice;

    @FindBy(xpath = "//input[@value='Delete']")
    public WebElement deleteButton;

    @FindBy(xpath = "//div[@id='sc-active-cart']//*[contains(text(),'Your Amazon Cart is empty')]")
    public WebElement cartEmpty;


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

    @Step("Waiting for quantity to load")
    public CartPage waitForDropdownToLoad() {
        logger.info("Waiting for dropdown menu to load options");
        super.waitForPageToLoad(quantityEquals2);
        return this;
    }

    @Step("Select dropdown menu")
    public CartPage selectDropdown() {
        logger.info("Selecting dropdown menu");
        dropdown.click();
        return this;
    }

    @Step("Change quantity to 2 from dropdown menu")
    public CartPage changeQuantityTo2() {
        logger.info("Changing quantity to 2 from dropdown menu");
        quantityEquals2.click();
        return this;
    }

    @Step("Wait for price update after quantity change")
    public CartPage waitForPriceToUpdate() {
        logger.info("Waiting for price update after quantity change");
        super.waitForPageToLoad(updatedPrice);
        return this;
    }

    @Step("Delete product from cart")
    public CartPage deleteProductFromCart() {
        logger.info("Deleting product from cart");
        deleteButton.click();
        return this;
    }


}
