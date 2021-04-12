package com.pdrewa.pageobject.amazon;

import com.pdrewa.PropertyManager;
import com.pdrewa.pageobject.BasePage;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProductDetailsPage extends BasePage {

    private static final Logger logger = LogManager.getLogger(ProductDetailsPage.class.getSimpleName());
    @FindBy(xpath = "//*[@id='price_inside_buybox']")
    public WebElement singleProductPrice;

    @FindBy(xpath = "//input[@id='add-to-cart-button']")
    public WebElement addToCartButton;


    public ProductDetailsPage(WebDriver driver, PropertyManager propertyManager) {
        super(driver, propertyManager);
    }

    @Step("Get single item price")
    public String getSingleItemPrice() {
        logger.info("Trying to get single item price at product page");
        return super.getPriceWithoutCurrency(singleProductPrice);
    }

    @Step("Add product to cart")
    public void addProductToCart() {
        logger.info("Trying to add product to cart");
        addToCartButton.click();
    }




}
