package com.pdrewa.drivers;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

public class CapabilityManager {

    public static ChromeOptions getChromeOptions(){
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setCapability("acceptInsecureCerts", true);
        chromeOptions.addArguments("start-maximized");
        return chromeOptions;
    }

    public static FirefoxOptions getFirefoxOptions() {
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        FirefoxProfile profile = new FirefoxProfile();
        profile.setAcceptUntrustedCertificates(true);
        profile.setPreference("network.proxy.type", 0);
        firefoxOptions.setProfile(profile);
        firefoxOptions.setCapability("acceptInsecureCerts", true);
        return firefoxOptions;
    }

    public static EdgeOptions getEdgeOptions(){
        EdgeOptions edgeOptions = new EdgeOptions();
        edgeOptions.setCapability("acceptInsecureCerts", true);
        edgeOptions.addArguments("start-maximized");
        return edgeOptions;
    }

}
