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

public class UserAccountPage implements IPageValidation {

    private static final Logger LOGGER = LogManager.getLogger();
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
        LOGGER.info("Wait until page will be loaded.");
        wait.until(ExpectedConditions.visibilityOf(userPic));
    }

    public UserAccountPage isAvailableUserPic() {
        LOGGER.info("Is available user pic");
        userPic.getText();
        return new UserAccountPage(driver, wait);
    }

    public String getActualUserName() {
        LOGGER.info("Get actual username.");
        waitToLoadPage();
        return actualUserName.getText();
    }
}


