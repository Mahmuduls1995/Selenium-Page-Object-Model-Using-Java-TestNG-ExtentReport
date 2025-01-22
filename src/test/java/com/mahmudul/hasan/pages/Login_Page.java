package com.mahmudul.hasan.pages;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.mahmudul.hasan.basedrivers.PageDriver;
import com.mahmudul.hasan.utilities.CommonMethods;
import com.mahmudul.hasan.utilities.ExcelUtils;
import com.mahmudul.hasan.utilities.Screenshots;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;

public class Login_Page extends CommonMethods {
    ExtentTest test;
    ExcelUtils excelutils = new ExcelUtils();

    // Locator for the username field
    @FindBys({@FindBy(xpath = "//input[@name='username']")})
    WebElement username;
    @FindBys({@FindBy(xpath = "//input[@name='password']")})
    WebElement password;
    @FindBys({@FindBy(xpath = "//button[@type='submit']")})
    WebElement login_button;


    // Locator for the login button

    public Login_Page(ExtentTest test) {
        PageFactory.initElements(PageDriver.getCurrentDriver(), this);
        this.test = test;
    }

    public void passCase(String message) {
        test.pass("<p style=\"color:#85BC63; font-size:13px\"><b>" + message + "</b></p>");
    }

    @SuppressWarnings("unused")
    public void passCaseWithSC(String message, String scName) throws IOException {
        test.pass("<p style=\"color:#85BC63; font-size:13px\"><b>" + message + "</b></p>");
        String screenShotPath = Screenshots.capture(PageDriver.getCurrentDriver(), scName); // Capture screenshot
        String dest = System.getProperty("user.dir") + "\\screenshots\\" + scName + ".png";
        test.info("Screenshot saved at: " + screenShotPath); // Add file path info to the report
        test.pass(MediaEntityBuilder.createScreenCaptureFromPath(dest).build());// Attach screenshot
    }

    // Fail
    @SuppressWarnings("unused")
    public void failCase(String message, String scName) throws IOException {
        test.fail("<p style=\"color:#FF5353; font-size:13px\"><b>" + message + "</b></p>");
        Throwable t = new InterruptedException("Exception");
        test.fail(t);
        String screenShotPath = Screenshots.capture(PageDriver.getCurrentDriver(), scName);
        String dest = System.getProperty("user.dir") + "\\screenshots\\" + scName + ".png";
        test.fail(MediaEntityBuilder.createScreenCaptureFromPath(dest).build());
        PageDriver.getCurrentDriver().quit();
    }

    public void login() throws IOException {

        try {
            excelutils.ReadExcel();
            test.info("Please enter the username");
            if (username.isDisplayed()) {
//              username.sendKeys("Admin");
                username.sendKeys(ExcelUtils.username);
                passCase("Username entered successfully");
                Thread.sleep(5000);
                try {
                    test.info("Please enter the password");
                    if (password.isDisplayed()) {
//                        password.sendKeys("admin123");
                        password.sendKeys(ExcelUtils.password);
                        passCase("Password entered successfully");
                        Thread.sleep(5000);
                        try {
                            test.info("Please click the login button");
                            if (login_button.isDisplayed()) {
                                login_button.click();
                                Thread.sleep(5000);
                                passCaseWithSC("You successfully logged in", "login_success");
                            } else {
                                failCase("Login button not found", "login_button_not_found");
                            }
                        } catch (Exception e) {
                            failCase("Login button not found", "login_button_not_found");
                        }
                    } else {
                        failCase("Password field not found", "password_field_not_found");
                    }
                } catch (Exception e) {
                    failCase("Password field not found", "password_field_not_found");
                }

            }


        } catch (Exception e) {


            failCase("User name field not found", "username_field_not_found");
        }


    }

}