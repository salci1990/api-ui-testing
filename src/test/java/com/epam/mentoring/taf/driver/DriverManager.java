package com.epam.mentoring.taf.driver;

import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;

import java.net.MalformedURLException;

import java.util.Objects;
import java.util.logging.LogManager;


public class DriverManager {

    protected static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static DriverManager instance = new DriverManager();
    private static final Logger LOGGER = (Logger) LogManager.getLogManager();

    private DriverManager() {
    }

    public void setWebDriver(BrowserType browser) throws MalformedURLException {
        if (Objects.isNull(driver.get())) ;
        driver.set(BrowserFactory.getBrowser(browser));
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void disposeDriver() {

        if (Objects.nonNull(driver)) {
            try {
                driver.get().quit();
                driver.remove();
            } catch (NoSuchSessionException e) {

            }
        }
        LOGGER.trace("The WebDriver driver was disposed.");
    }
}
