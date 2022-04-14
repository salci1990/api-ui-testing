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

    @Test
    public void uiVerification() {
        driver.findElement(By.partialLinkText("Sign in")).click();
        driver.findElement(By.className("form-control")).sendKeys(email);
        driver.findElement(By.cssSelector("input[formcontrolname=\'password\']")).sendKeys(password);
        driver.findElement(By.cssSelector("button[class=\'btn btn-lg btn-primary pull-xs-right\']")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("img[class=\'user-pic\']")));
        String actualUserName = driver.findElement(By.partialLinkText("Tom Marvolo Riddle")).getText();
        Assert.assertEquals(actualUserName, username);
    }

    @Test
    public void uiNegativeVerification() {
        driver.findElement(By.partialLinkText("Sign in")).click();
        driver.findElement(By.xpath("//input[@placeholder='Email']")).sendKeys(email);
        driver.findElement(By.cssSelector("input[formcontrolname=\'password\']")).sendKeys("wrong_password");
        driver.findElement(By.cssSelector("button[class=\'btn btn-lg btn-primary pull-xs-right\']")).click();

        String errorMessage = "email or password is invalid";
        Assert.assertEquals(errorMessage, "email or password is invalid");
    }

    @Test
    public void apiVerification() {
        given().baseUri(API_URL).when().contentType(ContentType.JSON).body(String.format("{\"user\":{\"email\":\"%s\",\"password\":\"%s\"}}", email, password)).post("/api/users/login").then().statusCode(200).body("user.email", is(email));
    }

    @Test
    public void apiNegativeVerification() {
        given().baseUri(API_URL).when().contentType(ContentType.JSON).body(String.format("{\"user\":{\"email\":\"%s\",\"password\":\"%s\"}}", email, "wrong_password")).post("/api/users/login").then().statusCode(422).body("errors.email or password", hasItem("is invalid"));
    }
}

