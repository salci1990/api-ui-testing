package com.epam.mentoring.taf.ui.pageobjects;

import com.epam.mentoring.taf.ui.pageobjects.interfaces.IPageValidation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage implements IPageValidation {

    private static final Logger LOGGER = LogManager.getLogger();
    WebDriver driver;
    WebDriverWait wait;

    @FindBy(css = "a[routerlink=\"/login\"]")
    private WebElement signInButton;

    @FindBy(css = "a[routerlink=\"/register\"]")
    private WebElement signUpButton;

    @FindBy(className ="logo-font")
    private WebElement homePageTitle;

    public HomePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    @Override
    public void waitToLoadPage() {
        LOGGER.info("Wait until page will be loaded.");
        wait.until(ExpectedConditions.visibilityOf(homePageTitle));
    }

    public SignInPage clickSignInButton() {
        LOGGER.info("Using SignIn button");
        signInButton.click();
        return new SignInPage(driver, wait);
    }

    public SignUpPage clickSginUpButton() {
        LOGGER.info("Using SignUp button");
        signUpButton.click();
        return new SignUpPage(driver, wait);
    }

}
