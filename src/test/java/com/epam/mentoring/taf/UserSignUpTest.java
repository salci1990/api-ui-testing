package com.epam.mentoring.taf;

import com.epam.mentoring.taf.api.models.dtos.ErrorsDTO;
import com.epam.mentoring.taf.api.models.dtos.UserDTO;
import com.epam.mentoring.taf.api.builders.UserBuilder;
import com.epam.mentoring.taf.api.endpoints.UserApi;
import com.epam.mentoring.taf.api.models.User;
import com.epam.mentoring.taf.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;


public class UserSignUpTest extends AbstractTest {

    private String userWithUniqueId;

    private UserApi userApi;
    private UserDTO userDTO;
    private ErrorsDTO errorDTO;
    private User existingUser;
    private User alreadyRegisteredUser;
    private User invalidEmailUser;

    @BeforeClass
    public void setupTest() {
        userApi = new UserApi();
        existingUser = Utils.getUserData("existing");
        alreadyRegisteredUser = Utils.getUserData("email or username already exist");
        invalidEmailUser = Utils.getUserData("invalid email");
    }

    @Test(groups = "UITests", description = "[UI Test] Successful sign up test")
    public void uiSignUpVerificationTest() {
        int uniqueId = Utils.generateRandomNumber();
        String uniqueUsername = Utils.generateRandomString(String.valueOf(uniqueId));
        String uniqueEmail = Utils.generateRandomEmail(uniqueUsername);

        homePage.clickSginUpButton();
        signUpPage
                .enterUserNameField(uniqueUsername)
                .enterEmailField(uniqueEmail)
                .enterPasswordField(existingUser.getPassword())
                .clickSignUpButton();

        Assert.assertEquals(userAccountPage.getActualUserName(), uniqueUsername);
    }

    @Test(groups = "UITests", description = "[UI Test] Log in invalid email or username")
    public void uiLogInWithInvalidEmailOrUsernameTest() throws Exception {

        homePage.clickSginUpButton();
        signUpPage
                .enterUserNameField(existingUser.getUsername())
                .enterEmailField(existingUser.getEmail())
                .enterPasswordField(existingUser.getPassword())
                .clickSignUpButton();

        Utils.takeScreenShot(driver, Utils.PATH_FILE);

        Assert.assertEquals(signUpPage.error(), "email has already been taken");
    }

    @Test(groups = "APITest", description = "[API Test] Successful login test")
    public void apiSuccessfulSignUpTest() {
        String email = existingUser.getEmail().replace("@", "." + Utils.generateRandomNumber() + "@");

        userDTO = new UserDTO(
                new UserBuilder()
                        .withUserName(existingUser.getUsername())
                        .withEmail(email)
                        .withPassword(existingUser.getPassword())
                        .build()
        );

        userApi
                .createNewUser(userDTO)
                .then()
                .assertThat()
                .statusCode(200)
                .body("user.username", is(existingUser.getUsername()))
                .body("user.email", is(email))
                .body("user.bio", nullValue())
                .body("user.token", notNullValue())
                .body("user.image", notNullValue());
    }

    @Test(groups = "APITest", description = "[API Test] Already registered user test")
    public void apiAlreadyRegisteredUserTest() {

        userDTO = new UserDTO(
                new UserBuilder()
                        .withUserName(alreadyRegisteredUser.getUsername())
                        .withEmail(alreadyRegisteredUser.getEmail())
                        .withPassword(alreadyRegisteredUser.getPassword())
                        .build()
        );

        userApi
                .createNewUser(userDTO)
                .then()
                .assertThat()
                .statusCode(422)
                .extract().as(ErrorsDTO.class);

        Assert.assertTrue(errorDTO.getErrors().getEmail().get(0).contains("has already been taken"));
        Assert.assertTrue(errorDTO.getErrors().getUsername().get(0).contains("has already been taken"));
    }

    @Test(enabled = false, groups = "APITest", description = "[API Test] Wrong Email during signup test")
    public void apiWrongEmailVerificationTest() {
        userWithUniqueId = Utils.generateRandomString(invalidEmailUser.getUsername());

        userDTO = new UserDTO(new UserBuilder()
                .withEmail(invalidEmailUser.getEmail())
                .withPassword(invalidEmailUser.getPassword())
                .withUserName(userWithUniqueId)
                .build()
        );

        errorDTO = userApi
                .createNewUser(userDTO)
                .then()
                .statusCode(422)
                .extract().as(ErrorsDTO.class);

        Assert.assertTrue(errorDTO.getErrors().getEmail().get(0).contains("has already been taken"));

    }
}
