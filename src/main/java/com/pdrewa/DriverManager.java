package com.pdrewa;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DriverManager {

    static final String CHROME_DRIVER_PATH = "drivers/windows/chromedriver.exe";
    private static final Logger log = LogManager.getLogger(DriverManager.class.getSimpleName());

    public WebDriver getDriver() {
        return initChrome();
    }

    private WebDriver initChrome() {
        log.info("Setting up new ChromeDriver");
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);
        return new ChromeDriver();
    }
}
