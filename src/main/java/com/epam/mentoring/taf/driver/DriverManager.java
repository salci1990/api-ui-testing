package com.epam.mentoring.taf.driver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.WebDriver;

import java.net.MalformedURLException;
import java.util.Objects;


public class DriverManager {

    private static final Logger LOGGER = LogManager.getLogger();
    protected static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static DriverManager instance = new DriverManager();


    private DriverManager() {
    }

    public void setWebDriver(BrowserType browser) throws MalformedURLException {
        LOGGER.info("Set browser using WebDriver.");
        driver.set(BrowserFactory.getBrowser(browser));
    }

    public WebDriver getDriver() {
        LOGGER.info("Get driver.");
        return driver.get();
    }

    public static DriverManager getInstance() {
        LOGGER.info("Get instance.");
        return instance;
    }

    public void disposeDriver() {

        if (Objects.nonNull(driver)) {
            try {
                driver.get().quit();
                driver.remove();
            } catch (NoSuchSessionException e) {
                e.printStackTrace();
            }
            LOGGER.info("Quit and remove driver.");
        }
    }
}
