package com.epam.mentoring.taf;

import io.restassured.http.ContentType;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

public class UserSignInTest extends AbstractTest {

    private final String username = "Tom Marvolo Riddle";
    private final String email = "tom_marvolo@example.com";
    private final String password = "Voldemort";

    @Test(groups = "UITest")
    public void uiSignInVerification() {

        homePage.clickSignInButton();
        Assert.assertEquals(signInPage.getSignInTitle(), "Sign in");
        signInPage
                .enterEmailField(email)
                .enterPasswordField(password)
                .clickSignInButton();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("img[class=\'user-pic\']")));
        String actualUserName = userAccountPage.returnActualUserName();
        Assert.assertEquals(actualUserName, username);
    }

    @Test(groups = "APITest")
    public void apiVerification() {
        given().log().all()
                .baseUri(API_URL)
                .when()
                .contentType(ContentType.JSON)
                .body(String.format("{\"user\":{\"email\":\"%s\",\"password\":\"%s\"}}", email, password))
                .log().all()
                .post("/api/users/login")
                .then()
                .statusCode(200)
                .body("user.email", is(email));
    }

    @Test(groups = "APITest")
    public void apiNegativeVerification() {
        given().log().all()
                .baseUri(API_URL)
                .when()
                .contentType(ContentType.JSON)
                .body(String.format("{\"user\":{\"email\":\"%s\",\"password\":\"%s\"}}", email, "wrong_password"))
                .log().all()
                .post("/api/users/login")
                .then()
                .statusCode(422)
                .body("errors.email or password", hasItem("is invalid"));
    }
}

