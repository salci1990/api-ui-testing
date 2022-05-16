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

public class SignInPage implements IPageValidation {

    private static final Logger LOGGER = LogManager.getLogger();
    WebDriver driver;
    WebDriverWait wait;

    @FindBy(partialLinkText = "Sign in")
    private WebElement signInTitle;

    @FindBy(css = "input[formcontrolname=\"email\"]")
    private WebElement emailField;

    @FindBy(css = "input[formcontrolname=\"password\"]")
    private WebElement passwordField;

    @FindBy(className = "btn")
    private WebElement signInButton;

    public SignInPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    @Override
    public void waitToLoadPage() {
        LOGGER.info("Wait until page will be loaded.");
        wait.until(ExpectedConditions.visibilityOf(signInTitle));
    }

    public SignInPage enterEmailField(String email) {
        LOGGER.info("Sending keys to email field", email);
        emailField.clear();
        emailField.sendKeys(email);
        return this;
    }

    public SignInPage enterPasswordField(String password) {
        LOGGER.info("Sending keys to password field", password);
        passwordField.clear();
        passwordField.sendKeys(password);
        return this;
    }

    public UserAccountPage clickSignInButton() {
        LOGGER.info("Using sigin button");
        signInButton.click();
        return new UserAccountPage(driver, wait);
    }

    public String getSignInTitle() {
        LOGGER.info("Return signin page title.");
        return signInTitle.getText();
    }
}