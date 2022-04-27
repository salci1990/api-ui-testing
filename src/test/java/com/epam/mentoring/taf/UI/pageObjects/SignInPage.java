package com.epam.mentoring.taf.UI.pageObjects;

import com.epam.mentoring.taf.UI.pageObjects.interfaces.IPageValidation;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SignInPage implements IPageValidation {

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
        wait.until(ExpectedConditions.visibilityOf(signInTitle));
    }

    public SignInPage enterEmailField(String email) {
        emailField.clear();
        emailField.sendKeys(email);
        return this;
    }

    public SignInPage enterPasswordField(String password) {
        passwordField.clear();
        passwordField.sendKeys(password);
        return this;
    }

    public UserAccountPage clickSignInButton() {
        signInButton.click();
        return new UserAccountPage(driver, wait);
    }

    public String getSignInTitle() {
        return signInTitle.getText();
    }
}