package com.epam.mentoring.taf.driver;

import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.WebDriver;

import java.net.MalformedURLException;
import java.util.Objects;


public class DriverManager {

    protected static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static DriverManager instance = new DriverManager();

    private DriverManager() {
    }

    public void setWebDriver(BrowserType browser) throws MalformedURLException {
        driver.set(BrowserFactory.getBrowser(browser));
    }

    public WebDriver getDriver() {
        return driver.get();
    }

    public static DriverManager getInstance() {
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
        }
    }
}
