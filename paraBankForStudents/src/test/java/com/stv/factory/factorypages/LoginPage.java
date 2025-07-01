package com.stv.factory.factorypages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class LoginPage extends BasePage {


    @FindBy(xpath = "//h2[normalize-space()='Sign in or Register']")
    private WebElement pageTitle;

    @FindBy(id = "Input_EmailAddress")
    private WebElement emailInput;

    @FindBy(id = "Input_Password")
    private WebElement passwordInput;

    @FindBy(className = "forgot-password")
    private WebElement forgottenPasswordLink;

    @FindBy(id = "emailSubmit")
    private WebElement continueSecurelyButton;

    @FindBy(id = "loginSubmit")
    private WebElement finalSignInButton;

    @FindBy(id = "Input_EmailAddress-error")
    private WebElement emailErrorMessage;

    @FindBy(className = "navbar-brand")
    private WebElement logoLink;

    @FindBy(id = "onetrust-accept-btn-handler")
    private WebElement allowAllCookiesButton;



    public LoginPage(WebDriver driver) {
        super(driver);
        try {
            System.out.println("LoginPage: Waiting for page title to be visible...");
            wait.until(ExpectedConditions.visibilityOf(pageTitle));
            System.out.println("LoginPage: Page with title '" + pageTitle.getText() + "' is loaded.");
        } catch (TimeoutException e) {
            System.err.println("Login page did not load correctly or title not found. Error: " + e.getMessage());

            throw e;
        }
        handleCookieBannerIfNeeded();
    }

    private void handleCookieBannerIfNeeded() {
        try {

            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(2));
            WebElement acceptButton = shortWait.until(ExpectedConditions.elementToBeClickable(allowAllCookiesButton));
            if (acceptButton.isDisplayed()) {
                System.out.println("LoginPage: Cookie banner found on login page. Clicking 'ALLOW ALL'.");
                acceptButton.click();
            }
        } catch (Exception e) {

            System.out.println("LoginPage: Cookie banner not found or already handled on login page.");
        }
    }


    public LoginPage enterEmail(String email) {
        System.out.println("LoginPage: Entering email: " + email);
        WebElement emailField = wait.until(ExpectedConditions.visibilityOf(emailInput));
        emailField.clear();
        emailField.sendKeys(email);
        return this;
    }

    public LoginPage enterPassword(String password) {
        System.out.println("LoginPage: Entering password.");
        WebElement passwordField = wait.until(ExpectedConditions.visibilityOf(passwordInput));
        passwordField.clear();
        passwordField.sendKeys(password);
        return this; // Для chaining
    }

    public LoginPage clickContinueSecurelyButton() {
        System.out.println("LoginPage: Clicking 'Continue Securely' button.");
        wait.until(ExpectedConditions.elementToBeClickable(continueSecurelyButton)).click();
        try {
            wait.until(ExpectedConditions.visibilityOf(passwordInput));
            System.out.println("LoginPage: Password input is now visible.");
        } catch (TimeoutException e) {
            System.out.println("LoginPage: Password input did not become visible after clicking Continue Securely (this may be expected).");
        }
        return this;
    }

    public ForgottenPasswordPage clickForgottenPasswordLink() {
        System.out.println("LoginPage: Clicking 'Forgotten your password?' link...");
        try {
            wait.until(ExpectedConditions.elementToBeClickable(forgottenPasswordLink)).click();
            System.out.println("LoginPage: 'Forgotten your password?' link clicked. Navigating to Forgotten Password Page.");
            return new ForgottenPasswordPage(driver);
        } catch (Exception e) {
            Assert.fail("Failed to click 'Forgotten your password?' link. Error: " + e.getMessage());
        }
        return null;
    }

    public MainPage clickFinalSignInButton() {
        System.out.println("LoginPage: Clicking final 'Sign In' button.");
        wait.until(ExpectedConditions.elementToBeClickable(finalSignInButton)).click();
        System.out.println("LoginPage: Clicked final sign-in. Navigating to MainPage.");
        return new MainPage(driver);
    }

    public MainPage clickLogoToReturnHome() {
        System.out.println("LoginPage: Clicking logo to return to Main Page...");
        try {
            WebElement logoElement = wait.until(ExpectedConditions.elementToBeClickable(logoLink));
            logoElement.click();
            System.out.println("LoginPage: Logo clicked. Navigating to Main Page.");

            new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.urlContains("www.wiggle.com"));
            System.out.println("LoginPage: Successfully navigated back to main site URL.");

            return new MainPage(driver);
        } catch (Exception e) {
            Assert.fail("Failed to click logo to return home. Check locator: @FindBy(className = \"navbar-brand\"). Error: " + e.getMessage());
        }
        return null;
    }


    public boolean isPageTitleDisplayed() {
        try {
            return pageTitle.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isEmailErrorMessageDisplayed() {
        try {

            return wait.until(ExpectedConditions.visibilityOf(emailErrorMessage)).isDisplayed();
        } catch (TimeoutException | NoSuchElementException e) {
            System.err.println("Email error message not found within timeout.");
            return false;
        }
    }

    public WebElement getPageTitleElement() {
        return wait.until(ExpectedConditions.visibilityOf(pageTitle));
    }

    public WebElement getEmailInputElement() {
        return wait.until(ExpectedConditions.visibilityOf(emailInput));
    }

    public WebElement getContinueSecurelyButtonElement() {
        return wait.until(ExpectedConditions.visibilityOf(continueSecurelyButton));
    }

    public WebElement getForgottenPasswordLinkElement() {
        return wait.until(ExpectedConditions.visibilityOf(forgottenPasswordLink));
    }

    public WebElement getActiveElementAfterTab() {
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return driver.switchTo().activeElement();
    }

    public void pressTabOnElement(WebElement element) {
        element.sendKeys(Keys.TAB);
    }
}