package com.epam.mentoring.taf.UI.pageObjects.interfaces.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.net.MalformedURLException;

public class BrowserFactory {

    public static WebDriver getBrowser(BrowserType browserType) throws MalformedURLException {
        switch (browserType) {
            case CHROME:
                WebDriverManager.chromedriver().setup();
                return new ChromeDriver();
            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                return new FirefoxDriver();
            case EDGE:
                WebDriverManager.edgedriver().setup();
                return new EdgeDriver();
            default:
                throw new IllegalArgumentException("Unknow browser type! Please check your configuration.");
        }
    }
}