package com.epam.mentoring.taf.UI.pageObjects;

import com.epam.mentoring.taf.UI.pageObjects.interfaces.IPageValidation;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SignUpPage implements IPageValidation {

    WebDriver driver;
    WebDriverWait wait;

    @FindBy(css = "h1[class=\"text-xs-center\"]")
    private WebElement signUpTitle;

    @FindBy(css = "input[formcontrolname=\"username\"]")
    private WebElement userNameField;

    @FindBy(css = "input[formcontrolname=\"email\"]")
    private WebElement emailField;

    @FindBy(css = "input[formcontrolname=\"password\"]")
    private WebElement passwordField;

    @FindBy(className = "btn")
    private WebElement signUpButton;

    public SignUpPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    @Override
    public void waitToLoadPage() {
        wait.until(ExpectedConditions.visibilityOf(signUpTitle));
    }

    public SignUpPage enterEmailField(String email) {
        emailField.sendKeys(email);
        return this;
    }

    public SignUpPage enterPasswordField(String password) {
        passwordField.sendKeys(password);
        return this;
    }

    public SignUpPage enterUserNameField(String username) {
        userNameField.sendKeys(username);
        return this;
    }

    public UserAccountPage clickSignUpButton() {
        signUpButton.click();
        return new UserAccountPage(driver, wait);
    }

    public String getSignUpTitle() {
        return signUpTitle.getText();
    }
}
