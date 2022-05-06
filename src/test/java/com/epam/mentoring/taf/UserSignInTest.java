package com.epam.mentoring.taf;

import com.epam.mentoring.taf.api.endpoints.LogInApi;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

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
                .assertThat()
                .body("user.username", is(username))
                .body("user.email", is(email))
                .body("user.image", notNullValue())
                .body("user.token", notNullValue())
                .statusCode(200);
    }

    @Test(groups = "APITest")
    public void wrongLogInTest() {
        logInApi.
                logInUser(String.format("{\"user\":{\"email\":\"%s\",\"password\":\"%s\"}}", email, "wrong_password"))
                .then()
                .assertThat()
                .statusCode(422)
                .body("errors.email or password", hasItem("is invalid"));
    }
}
