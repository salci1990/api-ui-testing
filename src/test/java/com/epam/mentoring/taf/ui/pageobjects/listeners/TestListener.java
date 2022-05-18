package com.epam.mentoring.taf.ui.pageobjects.listeners;

import com.epam.mentoring.taf.driver.DriverManager;
import io.qameta.allure.Attachment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class TestListener implements ITestListener {

    private static final Logger LOGGER = LogManager.getLogger();
    private static String getTestMethodName(ITestResult iTestResult) {
        return iTestResult.getMethod().getConstructorOrMethod().getName();
    }

    @Attachment
    public byte[] saveFailureScreenShot(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "{0}", type = "text/plain")
    public static String saveTextLog(String message) {
        return message;
    }

    @Override
    public void onTestFailure(ITestResult result) {
        List<String> groups = Arrays.asList(result.getMethod().getGroups());
        LOGGER.info("I am in onTestFailure method " + getTestMethodName(result) + " failed");
        if (groups.stream().anyMatch(group -> group.contains("UITests"))) {
            LOGGER.info("Screenshot captured for test case: " + getTestMethodName(result));
            saveFailureScreenShot(DriverManager.getInstance().getDriver());
        }
        saveTextLog(getTestMethodName(result) + "failed and screenshot taken!");
    }
}
