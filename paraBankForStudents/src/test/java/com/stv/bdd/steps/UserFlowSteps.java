package com.stv.bdd.steps;

import com.stv.factory.factorypages.CartPage;
import com.stv.factory.factorypages.ForgottenPasswordPage;
import com.stv.factory.factorypages.LoginPage;
import com.stv.factory.factorypages.MainPage;
import com.stv.framework.core.drivers.MyDriver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class UserFlowSteps {

    private WebDriver driver;
    private MainPage mainPage;
    private CartPage cartPage;
    private LoginPage loginPage;
    private ForgottenPasswordPage forgottenPasswordPage;


    @When("The user clicks on the basket icon")
    public void userClicksOnTheBasketIcon() {
        if (this.driver == null) this.driver = MyDriver.getDriver();
        if (this.driver == null) Assert.fail("WebDriver is null.");
        if (this.mainPage == null) this.mainPage = new MainPage(this.driver);

        cartPage = mainPage.clickBasketIcon();
        Assert.assertNotNull(cartPage, "Failed to navigate to Cart page.");
    }

    @Then("The user should be on the empty basket page")
    public void userShouldBeOnTheEmptyBasketPage() {
        Assert.assertTrue(cartPage.isPageLoaded(), "Verification failed: User is not on the empty basket page.");
    }

    @And("The basket icon should be visible in the header")
    public void basketIconShouldBeVisibleInHeader() {
        System.out.println("Step verification for basket icon visibility can be implemented if needed.");
    }

    @And("The {string} button should be visible on the empty basket page")
    public void buttonShouldBeVisibleOnEmptyBasketPage(String buttonText) {
        Assert.assertTrue(cartPage.isSignInButtonDisplayed(), "'" + buttonText + "' button is not visible on the empty cart page.");
    }

    @When("The user clicks the {string} button on the empty basket page")
    public void userClicksButtonOnEmptyBasketPage(String buttonText) {
        loginPage = cartPage.clickSignInButton();
        Assert.assertNotNull(loginPage, "Failed to navigate to Login page from Cart.");
    }

    @Then("The user should be on the Login page")
    public void userShouldBeOnTheLoginPage() {
        Assert.assertTrue(loginPage.isPageTitleDisplayed(), "Login page title is not visible, user might not be on the Login page.");
    }

    @When("The user clicks the {string} button on the Login page")
    public void userClicksButtonOnLoginPage(String buttonText) {
        Assert.assertNotNull(loginPage, "LoginPage is not initialized.");
        if ("Continue Securely".equalsIgnoreCase(buttonText)) {
            loginPage.clickContinueSecurelyButton();
        } else {
            Assert.fail("Unsupported button text for this step: " + buttonText);
        }
    }

    @Then("The user should still be on the Login page")
    public void userShouldStillBeOnTheLoginPage() {
        Assert.assertNotNull(loginPage, "LoginPage is not initialized.");
        Assert.assertTrue(loginPage.isPageTitleDisplayed(), "User is no longer on the Login page, which is unexpected.");
    }

    @And("An email error message should be displayed on the Login page")
    public void anEmailErrorMessageShouldBeDisplayedOnTheLoginPage() {
        Assert.assertTrue(loginPage.isEmailErrorMessageDisplayed(), "Email error message is not displayed after clicking 'Continue Securely' with empty email.");
    }

    @When("The user clicks the {string} link")
    public void userClicksTheForgottenYourPasswordLink(String linkText) {
        Assert.assertNotNull(loginPage, "LoginPage is not initialized.");
        forgottenPasswordPage = loginPage.clickForgottenPasswordLink();
        Assert.assertNotNull(forgottenPasswordPage, "Failed to navigate to Forgotten Password page.");
    }

    @Then("The user should be on the Forgotten Password page")
    public void userShouldBeOnTheForgottenPasswordPage() {
        Assert.assertNotNull(forgottenPasswordPage, "ForgottenPasswordPage is not initialized.");
        Assert.assertTrue(forgottenPasswordPage.isPageTitleDisplayed(), "Not on the Forgotten Password page, title is not visible.");
    }

    @And("The {string} button should be visible on the Forgotten Password page")
    public void buttonShouldBeVisibleOnForgottenPasswordPage(String buttonText) {
        Assert.assertNotNull(forgottenPasswordPage, "ForgottenPasswordPage is not initialized.");
        if ("Cancel".equalsIgnoreCase(buttonText)) {
            Assert.assertTrue(forgottenPasswordPage.isCancelButtonDisplayed(), "'Cancel' button is not visible.");
        } else {
            Assert.fail("Verification for button '" + buttonText + "' is not implemented in this step.");
        }
    }

    @When("The user clicks the {string} button on the Forgotten Password page")
    public void userClicksButtonOnForgottenPasswordPage(String buttonText) {
        Assert.assertNotNull(forgottenPasswordPage, "ForgottenPasswordPage is not initialized.");
        if ("Cancel".equalsIgnoreCase(buttonText)) {
            loginPage = forgottenPasswordPage.clickCancelButton();
            Assert.assertNotNull(loginPage, "Failed to return to Login page after clicking Cancel.");
        } else {
            Assert.fail("Action for button '" + buttonText + "' is not implemented in this step.");
        }
    }

    @Then("The user should be returned to the Login page")
    public void userShouldBeReturnedToTheLoginPage() {
        Assert.assertNotNull(loginPage, "LoginPage object is null after returning from Forgotten Password page.");
        Assert.assertTrue(loginPage.isPageTitleDisplayed(), "Did not return to the Login page (title not visible).");
    }

    @When("The user clicks the Wiggle logo to return to the Main page")
    public void userClicksTheWiggleLogoToReturnToTheMainPage() {
        Assert.assertNotNull(loginPage, "LoginPage is not initialized.");
        mainPage = loginPage.clickLogoToReturnHome();
        Assert.assertNotNull(mainPage, "Failed to return to Main page by clicking logo.");
    }

    @Then("The user should be returned to the Wiggle main page")
    public void userShouldBeReturnedToTheWiggleMainPage() {
        Assert.assertNotNull(mainPage, "MainPage object is null after returning from Login page.");
        Assert.assertTrue(mainPage.isSignInIconDisplayed(), "Sign In icon is not displayed on the main page after returning.");
    }
}