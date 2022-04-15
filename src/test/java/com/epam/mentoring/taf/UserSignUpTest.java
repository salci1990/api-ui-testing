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

    @Test
    public void uiVerification() {
        int uniqueId = (int) (Math.random() * 100);
        String username = this.username + uniqueId;
        String email = this.email.replace("@", "." + uniqueId + "@");

        driver.get(UI_URL);
        driver.findElement(By.partialLinkText("Sign up")).click();
        driver.findElement(By.cssSelector("input[placeholder=\'Username\']")).sendKeys(username);
        driver.findElement(By.cssSelector("input[formcontrolname=\'email\']")).sendKeys(email);
        driver.findElement(By.cssSelector("input[formcontrolname=\'password\']")).sendKeys(password);
        driver.findElement(By.cssSelector("button[class=\'btn btn-lg btn-primary pull-xs-right\']")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("img[class=\'user-pic\']")));
        String actualUserName = driver.findElement(By.xpath("//ul[contains(@class,'navbar-nav')]/li[4]/a")).getText();
        Assert.assertEquals(actualUserName, username);
    }

    @Test
    public void apiVerification() {
        int uniqueId = (int) (Math.random() * 100);
        String username = this.username + uniqueId;
        String email = this.email.replace("@", "." + uniqueId + "@");

        given().baseUri(API_URL).when().contentType(ContentType.JSON).body(String.format("{\"user\":{\"email\":\"%s\",\"password\":\"%s\",\"username\":\"%s\"}}", email, password, username)).post("/api/users").then().statusCode(200);
    }

    @Test
    public void apiAlreadyRegisteredVerification() {
        given().baseUri(API_URL).when().contentType(ContentType.JSON).body(String.format("{\"user\":{\"email\":\"%s\",\"password\":\"%s\",\"username\":\"%s\"}}", email, password, username)).post("/api/users").then().statusCode(422).body("errors.email", hasItem("has already been taken"));
    }

    @Test
    public void apiWrongEmailVerification() {
        given().baseUri(API_URL).when().contentType(ContentType.JSON).body(String.format("{\"user\":{\"email\":\"%s\",\"password\":\"%s\",\"username\":\"%s\"}}", "wrong_email", password, username)).post("/api/users").then().statusCode(422).body("errors.email", hasItem("is invalid"));
    }

}
