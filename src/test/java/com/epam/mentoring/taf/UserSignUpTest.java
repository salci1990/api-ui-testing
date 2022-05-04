package com.epam.mentoring.taf;

import com.epam.mentoring.taf.api.endpoints.SignUpApi;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

public class UserSignUpTest extends AbstractTest {

    private final String username = "Test UserTest";
    private final String email = "test_user@exampleTest.com";
    private final String password = "test_password";

    private SignUpApi signUpApi;

    @BeforeClass
    public void setupTest() {
        signUpApi = new SignUpApi();
    }

    @Test(groups = "UITests")
    public void uiSignUpVerificationTest() {
        int uniqueId = (int) (Math.random() * 100);
        String uniqueUsername = this.username + uniqueId;
        String uniqueEmail = this.email.replace("@", "." + uniqueId + "@");

        homePage.clickSginUpButton();
        signUpPage
                .enterUserNameField(uniqueUsername)
                .enterEmailField(uniqueEmail)
                .enterPasswordField(password)
                .clickSignUpButton();

        Assert.assertEquals(userAccountPage.getActualUserName(), uniqueUsername);
    }

    @Test(groups = "UITests")
    public void uiLogInWithInvalidEmailOrUsernameTest() {

        homePage.clickSginUpButton();
        signUpPage
                .enterUserNameField(username)
                .enterEmailField(email)
                .enterPasswordField(password)
                .clickSignUpButton();

        Assert.assertEquals(signUpPage.error(), "email has already been taken");
    }

    @Test(groups = "APITest")
    public void apiSuccesfulSignUpTest() {
        int uniqueId = (int) (Math.random() * 100);
        String username = this.username + uniqueId;
        String email = this.email.replace("@", "." + uniqueId + "@");

        signUpApi
                .signUpUser(String.format("{\"user\":{\"email\":\"%s\",\"password\":\"%s\",\"username\":\"%s\"}}", email, password, username))
                .then()
                .assertThat()
                .statusCode(200)
                .body("user.username", is(username))
                .body("user.email", is(email))
                .body("user.bio", nullValue())
                .body("user.token", notNullValue())
                .body("user.image", notNullValue());
    }

    @Test(groups = "APITest")
    public void apiAlreadyRegisteredUserTest() {

        signUpApi
                .signUpUser(String.format("{\"user\":{\"email\":\"%s\",\"password\":\"%s\",\"username\":\"%s\"}}", email, password, username))
                .then()
                .assertThat()
                .statusCode(403)
                .body("errors.email", hasItem("has already been taken"));

    }

    @Test(enabled = false, groups = "APITest", description = "api Wrong Email during signUp test")
    public void apiWrongEmailVerificationTest() {

        signUpApi
                .signUpUser(String.format("{\"user\":{\"email\":\"%s\",\"password\":\"%s\",\"username\":\"%s\"}}", "wrong_email", password, username))
                .then()
                .assertThat()
                .statusCode(422)
                .body("errors.email", hasItem("is invalid"));

    }
}
