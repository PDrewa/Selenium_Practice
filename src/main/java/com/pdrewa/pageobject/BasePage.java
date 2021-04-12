package com.pdrewa.pageobject;

import com.pdrewa.PropertyManager;
import com.pdrewa.pageobject.amazon.HomePage;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.v89.page.Page;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage extends Page {
    protected WebDriver driver;
    protected PropertyManager propertyManager;

    public BasePage(WebDriver driver, PropertyManager propertyManager) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
        this.propertyManager = propertyManager;
    }
    public String getTextFromWebElement(WebElement webElement) {
        return webElement.getText();
    }

    public String getPriceWithoutCurrency(WebElement webElement) {
        return getTextFromWebElement(webElement).substring(1);
    }

    public void waitForPageToLoad(WebElement webElement){
        new WebDriverWait(driver, Duration.ofSeconds(10), Duration.ofSeconds(1))
                .until(ExpectedConditions.visibilityOf(webElement));
    }

}
