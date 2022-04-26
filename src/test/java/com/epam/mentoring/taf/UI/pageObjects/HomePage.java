package com.epam.mentoring.taf.UI.pageObjects;

import com.epam.mentoring.taf.UI.pageObjects.interfaces.IPageValidation;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage implements IPageValidation {

    WebDriver driver;
    WebDriverWait wait;

    @FindBy(css = "a[routerlink=\"/login\"]")
    private WebElement signInButton;

    @FindBy(css = "a[routerlink=\"/register\"]")
    private WebElement signUpButton;

    public HomePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    @Override
    public void waitToLoadPage() {

    }

    public SignInPage clickSignInButton() {
        signInButton.click();
        return new SignInPage(driver, wait);
    }

    public SignUpPage clickSginUpBatton() {
        signUpButton.click();
        return new SignUpPage(driver, wait);
    }

}
