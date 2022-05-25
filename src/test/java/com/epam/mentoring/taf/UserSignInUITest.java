package com.epam.mentoring.taf;

import com.epam.mentoring.taf.api.models.User;
import com.epam.mentoring.taf.ui.pageobjects.listeners.TestListener;
import com.epam.mentoring.taf.utils.Utils;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners({TestListener.class})
public class UserSignInUITest extends AbstractTest {

    private User wrongLoginUser;
    private User newUser;

    @BeforeClass
    public void setupTest() {
        newUser = Utils.getUserData("existing");
    }

    @Test(groups = "UITests", description = "Successful singin test")
    @Story("Story: Signin Presence")
    @Severity(SeverityLevel.BLOCKER)
    public void uiCorrectSignInUITest() {

        homePage.clickSignInButton();
        signInPage
                .enterEmailField(newUser.getEmail())
                .enterPasswordField(newUser.getPassword())
                .clickSignInButton();

        Assert.assertEquals(userAccountPage.getActualUserName(), this.newUser.getUsername());
    }
}
