package com.epam.mentoring.taf;

import com.epam.mentoring.taf.api.apidto.ErrorsDTO;
import com.epam.mentoring.taf.api.apidto.UserDTO;
import com.epam.mentoring.taf.api.builders.UserBuilder;
import com.epam.mentoring.taf.api.endpoints.UserApi;
import com.epam.mentoring.taf.api.models.User;
import com.epam.mentoring.taf.utils.Utils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

public class UserSignUpTest extends AbstractTest {

    private String userWithUniqueId;

    private UserApi userApi;
    private UserDTO userDTO;
    private ErrorsDTO errorDTO;
    private User invalidUser;
    private User user;

    @BeforeClass
    public void setupTest() {
        userApi = new UserApi();
    }

    @Test(groups = "UITests")
    public void uiSignUpVerificationTest() {
        int uniqueId = Utils.generateRandomNumber();
        String uniqueUsername = Utils.generateRandomString(String.valueOf(uniqueId));
        String uniqueEmail = Utils.generateRandomEmail(uniqueUsername);

        homePage.clickSginUpButton();
        signUpPage
                .enterUserNameField(uniqueUsername)
                .enterEmailField(uniqueEmail)
                .enterPasswordField(user.getPassword())
                .clickSignUpButton();

        Assert.assertEquals(userAccountPage.getActualUserName(), uniqueUsername);
    }

    @Test(groups = "UITests")
    public void uiLogInWithInvalidEmailOrUsernameTest() {

        homePage.clickSginUpButton();
        signUpPage
                .enterUserNameField(user.getUsername())
                .enterEmailField(user.getEmail())
                .enterPasswordField(user.getPassword())
                .clickSignUpButton();

        Assert.assertEquals(signUpPage.error(), "email has already been taken");
    }

    @Test(groups = "APITest")
    public void apiSuccesfulSignUpTest() {
        String email = user.getEmail().replace("@", "." + Utils.generateRandomNumber() + "@");

        userDTO = new UserDTO(
                new UserBuilder()
                        .withUserName(user.getUsername())
                        .withEmail(user.getEmail())
                        .withPassword(user.getPassword())
                        .build()
        );

        userApi
                .createNewUser(userDTO, true)
                .then()
                .assertThat()
                .statusCode(200)
                .body("user.username", is(user.getUsername()))
                .body("user.email", is(email))
                .body("user.bio", nullValue())
                .body("user.token", notNullValue())
                .body("user.image", notNullValue());
    }

    @Test(groups = "APITest")
    public void apiAlreadyRegisteredUserTest() {

        userDTO = new UserDTO(
                new UserBuilder()
                        .withUserName(invalidUser.getUsername())
                        .withEmail(invalidUser.getEmail())
                        .withPassword(invalidUser.getPassword())
                        .build()
        );

        userApi
                .createNewUser(userDTO, true)
                .then()
                .assertThat()
                .statusCode(422)
                .extract().as(ErrorsDTO.class);

        Assert.assertTrue(errorDTO.getErrors().getEmail().get(0).contains("has already been taken"));
        Assert.assertTrue(errorDTO.getErrors().getUsername().get(0).contains("has already been taken"));
    }

    @Test(enabled = false, groups = "APITest", description = "api Wrong Email during signUp test")
    public void apiWrongEmailVerificationTest() {
        userWithUniqueId = Utils.generateRandomString(invalidUser.getUsername());

        userDTO = new UserDTO(new UserBuilder()
                .withEmail(invalidUser.getEmail())
                .withPassword(invalidUser.getPassword())
                .withUserName(userWithUniqueId)
                .build()
        );

        errorDTO = userApi
                .createNewUser(userDTO, true)
                .then()
                .statusCode(422)
                .extract().as(ErrorsDTO.class);

        Assert.assertTrue(errorDTO.getErrors().getEmail().get(0).contains("has already been taken"));

    }
}
