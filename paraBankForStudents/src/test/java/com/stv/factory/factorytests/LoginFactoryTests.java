package com.stv.factory.factorytests;

import com.stv.factory.factorypages.LoginPage;
import com.stv.factory.factorypages.MainPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginFactoryTests extends BaseFactoryTest {

    private MainPage mainPageInstance;

    @BeforeMethod
    public void pageSetUp() {
        mainPageInstance = new MainPage(driver);
        mainPageInstance.open();
    }

    @Test(description = "Test successful login scenario")
    public void testSuccessfulLogin() {

        LoginPage loginPage = mainPageInstance.clickSignInEntryPoint();
        Assert.assertTrue(loginPage.getEmailInputElement().isDisplayed(), "Email input should be displayed on Login Page.");

        String testEmail = "katsiaryna.schastnaya@student.ehu.lt";
        String testPassword = "18102005";

        MainPage loggedInMainPage = loginPage.enterEmail(testEmail)
                .clickContinueSecurelyButton()
                .enterPassword(testPassword)
                .clickFinalSignInButton();

        Assert.assertTrue(loggedInMainPage.isUserLoggedIn(), "User should be logged in on the Main Page.");
        System.out.println("TestSuccessfulLogin: Successfully logged in and verified user is on Main Page (logged in state).");
    }

    @Test(description = "Verify visibility of initial login form elements")
    public void testInitialLoginFormElementsAreVisible() {
        LoginPage loginPage = mainPageInstance.clickSignInEntryPoint();
        Assert.assertTrue(loginPage.getEmailInputElement().isDisplayed(), "Email input should be displayed.");
        Assert.assertTrue(loginPage.getContinueSecurelyButtonElement().isDisplayed(), "'Continue Securely' button should be displayed.");
        Assert.assertTrue(loginPage.getForgottenPasswordLinkElement().isDisplayed(), "Forgotten Password link should be displayed.");
        System.out.println("TestInitialLoginFormElementsAreVisible: Verified initial elements on login page.");
    }
}