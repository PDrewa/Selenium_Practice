package com.pdrewa.pageobject.amazon;


import com.pdrewa.PropertyManager;
import com.pdrewa.pageobject.BasePage;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class HomePage extends BasePage {

    private static final Logger logger = LogManager.getLogger(HomePage.class.getSimpleName());
    public final String PAGE_TITLE = propertyManager.getProperty("homepage.title");
    private final String HOME_PAGE_URL = propertyManager.getProperty("homepage.url");

    @FindBy(xpath = "//input[@id='twotabsearchtextbox']")
    public WebElement searchField;

    @FindBy(xpath = "//input[@id='sp-cc-accept']")
    public WebElement acceptCookies;

    public HomePage(WebDriver driver, PropertyManager propertyManager) {
        super(driver, propertyManager);
    }

    @Step("Open application home page")
    public HomePage open() {
        logger.info("Trying to open application home page");
        driver.navigate().to(HOME_PAGE_URL);
        Assert.assertEquals(PAGE_TITLE, driver.getTitle());
        return this;
    }

    @Step("Search for a product with name: {productName}")
    public HomePage searchByProductName(String productName) {
        logger.info("Performing search for product with title: " + productName);
        searchField.sendKeys(productName);
        searchField.sendKeys(Keys.ENTER);
        return this;
    }

    @Step("Accept cookies if appear")
    public HomePage acceptCookiesIfPopupPresent() {
        try {
            acceptCookies.click();
        } catch (NoSuchElementException e) {
            logger.info("Cookies pop-up is not present on page");
        }
        return this;
    }

}
