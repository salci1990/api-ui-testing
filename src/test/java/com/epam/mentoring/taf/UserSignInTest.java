package com.epam.mentoring.taf;

import com.epam.mentoring.taf.api.models.dtos.ErrorsDTO;
import com.epam.mentoring.taf.api.models.dtos.UserDTO;
import com.epam.mentoring.taf.api.builders.UserBuilder;
import com.epam.mentoring.taf.api.endpoints.LogInApi;
import com.epam.mentoring.taf.api.models.User;
import com.epam.mentoring.taf.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.contains;

public class UserSignInTest extends AbstractTest {

    private LogInApi logInApi;
    private UserDTO userDTO;
    private User wrongLoginUser;
    private User newUser;

    @BeforeClass
    public void setupTest() {
        logInApi = new LogInApi();
        newUser = Utils.getUserData("existing");
        wrongLoginUser = Utils.getUserData("wrong login");
    }

    @Test(groups = "UITests")
    public void uiCorrectSignInVeryficationTest() {

        homePage.clickSignInButton();
        signInPage
                .enterEmailField(newUser.getEmail())
                .enterPasswordField(newUser.getPassword())
                .clickSignInButton();

        Assert.assertEquals(userAccountPage.getActualUserName(), this.newUser.getUsername());
    }

    @Test(groups = "APITest", description = "[API Test] Successful login test")
    public void successfulLogInTest() {

        userDTO = new UserDTO(
                new UserBuilder()
                        .withUserName(newUser.getUsername())
                        .withEmail(newUser.getEmail())
                        .withPassword(newUser.getPassword())
                        .build()
        );

        logInApi
                .logInUser(userDTO)
                .then()
                .assertThat()
                .body("user.username", is(newUser.getUsername()))
                .body("user.email", is(newUser.getEmail()))
                .body("user.image", notNullValue())
                .body("user.token", notNullValue())
                .statusCode(200);
    }

    @Test(groups = "APITest", description = "[API Test] Invalid login with wrong password")
    public void wrongLogInTest() {

        userDTO = new UserDTO(
                new UserBuilder()
                        .withUserName(wrongLoginUser.getUsername())
                        .withEmail(wrongLoginUser.getEmail())
                        .withPassword(wrongLoginUser.getPassword())
                        .build()
        );

        logInApi.
                logInUser(userDTO)
                .then()
                .assertThat()
                .statusCode(422)
                .body("errors", hasKey("email or password"),
                        "errors.'email or password'", hasSize(1),
                        "errors.'email or password'", contains("is invalid")
                );
    }
}
