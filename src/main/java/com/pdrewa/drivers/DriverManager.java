package com.pdrewa.drivers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

public class DriverManager {
    private static final Logger log = LogManager.getLogger(DriverManager.class.getSimpleName());

    public WebDriver getDriver(String driverType) {
        return getDriver(driverType, "LOCAL");
    }

    public WebDriver getDriver(String driverType, String gridMode) {

        WebDriver requestedDriver = null;
        switch (driverType.toUpperCase(Locale.ROOT)) {
            case "CHROME": {
                log.info("Chrome driver selected, grid mode: " + gridMode);
                requestedDriver = gridMode.toUpperCase(Locale.ROOT).equals("GRID") ?
                        getRemoteDriver(CapabilityManager.getChromeOptions()) : new ChromeDriver(CapabilityManager.getChromeOptions());
                break;
            }
            case "FIREFOX": {
                log.info("Firefox driver selected, grid mode: " + gridMode);
                requestedDriver = gridMode.toUpperCase(Locale.ROOT).equals("GRID") ?
                        getRemoteDriver(CapabilityManager.getFirefoxOptions()) : new FirefoxDriver(CapabilityManager.getFirefoxOptions());
                break;
            }
            case "EDGE": {
                log.info("Edge driver selected, grid mode: " + gridMode);
                requestedDriver = gridMode.toUpperCase(Locale.ROOT).equals("GRID") ?
                        getRemoteDriver(CapabilityManager.getEdgeOptions()) : new EdgeDriver(CapabilityManager.getEdgeOptions());
                break;
            }
        }
        return requestedDriver;
    }

    private WebDriver getRemoteDriver(Capabilities capabilities) {
        WebDriver requestedDriver = null;
        try {

            requestedDriver = new RemoteWebDriver(new URL(System.getProperty("selenium.grid.url")), capabilities);
        } catch (MalformedURLException e) {
            log.error("Remote driver creation failed");
        }
        return requestedDriver;
    }
}
