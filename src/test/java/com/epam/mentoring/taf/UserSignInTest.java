package com.epam.mentoring.taf;

import com.epam.mentoring.taf.api.apidto.ErrorsDTO;
import com.epam.mentoring.taf.api.apidto.UserDTO;
import com.epam.mentoring.taf.api.builders.UserBuilder;
import com.epam.mentoring.taf.api.endpoints.LogInApi;
import com.epam.mentoring.taf.api.models.User;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

public class UserSignInTest extends AbstractTest {

    private LogInApi logInApi;
    private UserDTO userDTO;
    private User invalidUser;
    private User user;

    @BeforeClass
    public void setupTest() {
        logInApi = new LogInApi();
    }

    @Test(groups = "UITests")
    public void uiCorrectSignInVeryficationTest() {

        homePage.clickSignInButton();
        signInPage
                .enterEmailField(user.getEmail())
                .enterPasswordField(user.getPassword())
                .clickSignInButton();

        Assert.assertEquals(userAccountPage.getActualUserName(), this.user.getUsername());
    }

    @Test(groups = "APITest")
    public void succesfulLogInTest() {

        userDTO = new UserDTO(
                new UserBuilder()
                        .withUserName(invalidUser.getUsername())
                        .withEmail(invalidUser.getEmail())
                        .withPassword(invalidUser.getPassword())
                        .build()
        );

        logInApi
                .logInUser(userDTO, true)
                .then()
                .assertThat()
                .body("user.username", is(user.getUsername()))
                .body("user.email", is(user.getEmail()))
                .body("user.image", notNullValue())
                .body("user.token", notNullValue())
                .statusCode(200)
                .extract().as(ErrorsDTO.class);
    }

    @Test(groups = "APITest", description = "[API Test] Invalid login with wrong password")
    public void wrongLogInTest() {

        userDTO = new UserDTO(
                new UserBuilder()
                        .withUserName(invalidUser.getUsername())
                        .withEmail(invalidUser.getEmail())
                        .withPassword(invalidUser.getPassword())
                        .build()
        );

        logInApi.
                logInUser(userDTO, true)
                .then()
                .assertThat()
                .statusCode(422)
                .body("errors", hasKey("email or password"),
                        "errors.'email or password'", hasSize(1),
                        "errors.'email or password'", contains("is invalid")
                );
    }
}
