package com.epam.mentoring.taf.UI.pageObjects;

import com.epam.mentoring.taf.UI.pageObjects.interfaces.IPageValidation;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UserAccountPage implements IPageValidation {

    WebDriver driver;
    WebDriverWait wait;

    @FindBy(css = "img[class=\'user-pic\']")
    private WebElement userPic;

    @FindBy(xpath = "//ul[contains(@class,'navbar-nav')]/li[4]/a")
    private WebElement actualUserName;

    public UserAccountPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    @Override
    public void waitToLoadPage() {
        wait.until(ExpectedConditions.visibilityOf(userPic));
    }

    public UserAccountPage isAvailableUserPic() {
        userPic.getText();
        return new UserAccountPage(driver, wait);
    }

    public String returnActualUserName() {
        return actualUserName.getText();
    }
}


