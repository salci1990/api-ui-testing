package com.epam.mentoring.taf;

import com.epam.mentoring.taf.ui.pageobjects.HomePage;
import com.epam.mentoring.taf.ui.pageobjects.SignInPage;
import com.epam.mentoring.taf.ui.pageobjects.SignUpPage;
import com.epam.mentoring.taf.ui.pageobjects.UserAccountPage;
import com.epam.mentoring.taf.driver.BrowserType;
import com.epam.mentoring.taf.driver.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.net.MalformedURLException;
import java.net.URL;

import static com.epam.mentoring.taf.utils.Utils.CONFIG_DATA;

abstract public class AbstractTest {

    protected final static String baseUrl = "https://angular.realworld.io";
    protected final static String UI_URL = "https://angular.realworld.io";
    protected final static String API_URL = "https://api.realworld.io/";

    protected WebDriver driver;
    protected WebDriverWait wait;

    HomePage homePage;
    SignInPage signInPage;
    SignUpPage signUpPage;
    UserAccountPage userAccountPage;

    private static final Logger LOGGER = LogManager.getLogger();

    @BeforeMethod(onlyForGroups = "UITests")
    public void setup() throws MalformedURLException {

        initialisation();
        homePage = new HomePage(driver, wait);
        signInPage = new SignInPage(driver, wait);
        signUpPage = new SignUpPage(driver, wait);
        userAccountPage = new UserAccountPage(driver, wait);
    }

    public void initialisation() throws MalformedURLException {
        LOGGER.info("Prepare every setting to start testing run.");
        String gridUrl = System.getProperty("grid.url");
        if (gridUrl == null) {
            DriverManager.getInstance().setWebDriver(BrowserType.valueOf(CONFIG_DATA.driverType()));
        } else {
            driver = new RemoteWebDriver(new URL(gridUrl), DesiredCapabilities.chrome());
        }
        driver = DriverManager.getInstance().getDriver();
        driver.get(CONFIG_DATA.uiUrl());
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, CONFIG_DATA.waitTimeoutInSeconds());
    }

    @AfterMethod(onlyForGroups = "UITests")
    public void terminate() {
        driver.quit();
    }

}
