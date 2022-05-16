package com.epam.mentoring.taf.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.net.MalformedURLException;

import static com.epam.mentoring.taf.driver.BrowserType.*;

public class BrowserFactory {

    private static final Logger LOGGER = LogManager.getLogger();
    public static WebDriver getBrowser(BrowserType browserType) throws MalformedURLException {
        switch (browserType) {
            case CHROME:
                WebDriverManager.chromedriver().setup();
                LOGGER.info("Setup chrome browser.");
                return new ChromeDriver();
            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                LOGGER.info("Setup firefox browser.");
                return new FirefoxDriver();
            case EDGE:
                WebDriverManager.edgedriver().setup();
                LOGGER.info("Setup edge browser.");
                return new EdgeDriver();
            default:
                throw new IllegalArgumentException("Unknow browser type! Please check your configuration.");
        }
    }
}