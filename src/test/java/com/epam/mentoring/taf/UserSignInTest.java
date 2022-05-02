package com.epam.mentoring.taf;

import com.epam.mentoring.taf.api.endpoints.LogInApi;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

public class UserSignInTest extends AbstractTest {

    private final String username = "Tom Marvolo Riddle";
    private final String email = "tom_marvolo@example.com";
    private final String password = "Voldemort";

    private LogInApi logInApi;

    @BeforeClass
    public void setupTest() {
        logInApi = new LogInApi();
    }

    @Test(groups = "UITests")
    public void uiCorrectSignInVeryficationTest() {

        homePage.clickSignInButton();
        signInPage
                .enterEmailField(email)
                .enterPasswordField(password)
                .clickSignInButton();

        Assert.assertEquals(userAccountPage.getActualUserName(), username);
    }

    @Test(groups = "APITest")
    public void succesfulLogInTest() {

        logInApi
                .logInUser(String.format("{\"user\":{\"email\":\"%s\",\"password\":\"%s\"}}", email, password))
                .then()
                .statusCode(200)
                .body("user.email", is(email));

    }

    @Test(groups = "APITest")
    public void wrongLogInTest() {
        logInApi.
                logInUser(String.format("{\"user\":{\"email\":\"%s\",\"password\":\"%s\"}}", email, "wrong_password"))
                .then()
                .statusCode(422)
                .body("errors.email or password", hasItem("is invalid"));
    }
}
