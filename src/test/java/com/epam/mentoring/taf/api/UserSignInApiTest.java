package com.epam.mentoring.taf.api;

import com.epam.mentoring.taf.api.builders.UserBuilder;
import com.epam.mentoring.taf.api.endpoints.LogInApi;
import com.epam.mentoring.taf.api.models.User;
import com.epam.mentoring.taf.api.models.dtos.UserDTO;
import com.epam.mentoring.taf.utils.Utils;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.contains;

public class UserSignInApiTest {

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

    @Test(groups = "APITest", description = "[API Test] Successful login test")
    @Story("Story: Login Presence")
    @Severity(SeverityLevel.BLOCKER)
    public void apiSuccessfulLogInApiTest() {

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
                .body("user.image", nullValue())
                .body("user.token", notNullValue())
                .statusCode(200);
    }

    @Test(groups = "APITest", description = "[API Test] Invalid login with wrong password")
    @Story("Story: Wrong Login Presence")
    @Severity(SeverityLevel.NORMAL)
    public void wrongLogInApiTest() {

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
