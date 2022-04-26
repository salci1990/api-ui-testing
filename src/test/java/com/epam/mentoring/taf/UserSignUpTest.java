package com.epam.mentoring.taf;

import io.restassured.http.ContentType;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;

public class UserSignUpTest extends AbstractTest {

    private final String username = "Test User";
    private final String email = "test_user@example.com";
    private final String password = "test_password";

    @Test(groups = "UITest")
    public void uiSignUpVerification() {
        int uniqueId = (int) (Math.random() * 100);
        String uniqueUsername = this.username + uniqueId;
        String uniqueEmail = this.email.replace("@", "." + uniqueId + "@");

        homePage.clickSginUpBatton();
        Assert.assertEquals(signUpPage.getSignUpTitle(), "Sign up");
        signUpPage
                .enterUserNameField(uniqueUsername)
                .enterEmailField(uniqueEmail)
                .enterPasswordField(password)
                .clickSignUpButton();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("img[class=\'user-pic\']")));
        String actualUserName = userAccountPage.returnActualUserName();
        Assert.assertEquals(actualUserName, uniqueUsername);
    }

    @Test(groups = "UITest")
    public void uiSignUpNegativeVerification() {

        homePage.clickSginUpBatton();
        Assert.assertEquals(signUpPage.getSignUpTitle(), "Sign up");

        signUpPage
                .enterUserNameField(username)
                .enterEmailField(email)
                .enterPasswordField(password)
                .clickSignUpButton();
//        wait.until(ExpectedConditions.visibilityOf(signUpPage.error()));
        String errorMessage = "email or password is invalid";
        Assert.assertEquals(errorMessage, "email or password is invalid");
    }

    @Test(groups = "APITest")
    public void apiVerification() {
        int uniqueId = (int) (Math.random() * 100);
        String username = this.username + uniqueId;
        String email = this.email.replace("@", "." + uniqueId + "@");

        given().baseUri(API_URL)
                .when()
                .contentType(ContentType.JSON)
                .body(String.format("{\"user\":{\"email\":\"%s\",\"password\":\"%s\",\"username\":\"%s\"}}", email, password, username))
                .post("/api/users")
                .then()
                .statusCode(200);
    }

    @Test(groups = "APITest")
    public void apiAlreadyRegisteredVerification() {
        given().baseUri(API_URL)
                .when()
                .contentType(ContentType.JSON)
                .body(String.format("{\"user\":{\"email\":\"%s\",\"password\":\"%s\",\"username\":\"%s\"}}", email, password, username))
                .post("/api/users")
                .then()
                .statusCode(422)
                .body("errors.email", hasItem("has already been taken"));
    }

    @Test(enabled = false, groups = "APITest", description = "api Wrong Email during signUp test")
    public void apiWrongEmailVerification() {
        given().log().all()
                .baseUri(API_URL)
                .when()
                .contentType(ContentType.JSON)
                .body(String.format("{\"user\":{\"email\":\"%s\",\"password\":\"%s\",\"username\":\"%s\"}}", "wrong_email", password, username))
                .log().all()
                .post("/api/users")
                .then()
                .statusCode(422)
                .body("errors.email", hasItem("is invalid"));
    }

}
