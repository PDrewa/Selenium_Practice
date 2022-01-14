package com.pdrewa.pageobject.amazon;

import com.pdrewa.PropertyManager;
import com.pdrewa.pageobject.BasePage;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class SearchResultsPage extends BasePage {

    private static final Logger logger = LogManager.getLogger(SearchResultsPage.class.getSimpleName());

    @FindAll(@FindBy(xpath = "//div[@id='departments']//span[@class='a-size-base a-color-base']"))
    public List<WebElement> leftMenuCatogories;

    @FindBy(xpath = "//*[contains(text(),'Refurbished')]")
    public WebElement specificProduct;


    public SearchResultsPage(WebDriver driver, PropertyManager propertyManager) {
        super(driver, propertyManager);
    }


    @Step("Select category with {position} position in search results")
    public SearchResultsPage selectCategoryByPosition(int position) {
        logger.info("Trying to select category with position: " + position + " from search results");
        leftMenuCatogories.get(position - 1).click();
        return this;
    }

    public SearchResultsPage selectCategoryByPosition(String position) {
        return selectCategoryByPosition(Integer.parseInt(position));
    }

    @Step("Select specifi product")
    public SearchResultsPage selectSpecificProduct() {
        logger.info("Trying to select specific product from search results");
        specificProduct.click();
        return this;
    }


}
