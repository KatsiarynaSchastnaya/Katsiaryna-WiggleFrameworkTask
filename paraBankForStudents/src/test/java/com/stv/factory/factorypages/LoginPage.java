
package com.stv.factory.factorypages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginPage extends BasePage {

    @FindBy(xpath = "//h2[normalize-space()='Sign in or Register']")
    private WebElement pageTitle;

    @FindBy(id = "Input_EmailAddress")
    private WebElement emailInput;

    @FindBy(xpath = "//a[contains(@class,'forgot-password') and contains(text(),'Forgotten your password?')]")
    private WebElement forgottenPasswordLink;

    @FindBy(id = "emailSubmit")
    private WebElement continueSecurelyButton;

    @FindBy(xpath = "//a[contains(@href, 'policies.google.com/privacy') and normalize-space()='Конфиденциальность']")
    private WebElement privacyPolicyLink;

    @FindBy(xpath = "//a[contains(@href, 'policies.google.com/terms') and normalize-space()='Условия использования']")
    private WebElement termsOfUseLink;

    @FindBy(id = "Input_Password")
    private WebElement passwordInput;

    @FindBy(id = "loginSubmit")
    private WebElement finalSignInButton;

    @FindBy(id = "onetrust-accept-btn-handler")
    private WebElement allowAllCookiesButton;

    public LoginPage(WebDriver driver) {
        super(driver);
        try {
            System.out.println("LoginPage: Waiting for email input (id=Input_EmailAddress) to be visible...");
            wait.until(ExpectedConditions.visibilityOf(emailInput));
            System.out.println("LoginPage: Email input is visible.");
        } catch (TimeoutException e) {
            System.err.println("LoginPage: Email input (id=Input_EmailAddress) NOT visible on Login Page after timeout!");
            throw e;
        }
        handleCookieBannerIfNeeded();
    }

    private void handleCookieBannerIfNeeded() {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(2));
            WebElement acceptButton = shortWait.until(ExpectedConditions.elementToBeClickable(allowAllCookiesButton));
            if (acceptButton.isDisplayed()){
                System.out.println("LoginPage: Cookie banner found on login page. Clicking 'ALLOW ALL'.");
                acceptButton.click();
            }
        } catch (Exception e) {
            System.out.println("LoginPage: Cookie banner not found or already handled on login page.");
        }
    }

    public WebElement getPageTitleElement() {
        wait.until(ExpectedConditions.visibilityOf(pageTitle));
        return pageTitle;
    }

    public WebElement getEmailInputElement() {
        wait.until(ExpectedConditions.visibilityOf(emailInput));
        return emailInput;
    }

    public WebElement getForgottenPasswordLinkElement() {
        wait.until(ExpectedConditions.visibilityOf(forgottenPasswordLink));
        return forgottenPasswordLink;
    }

    public WebElement getContinueSecurelyButtonElement() {
        wait.until(ExpectedConditions.visibilityOf(continueSecurelyButton));
        return continueSecurelyButton;
    }

    public WebElement getPrivacyPolicyLinkElement() {
        wait.until(ExpectedConditions.visibilityOf(privacyPolicyLink));
        return privacyPolicyLink;
    }

    public WebElement getTermsOfUseLinkElement() {
        wait.until(ExpectedConditions.visibilityOf(termsOfUseLink));
        return termsOfUseLink;
    }

    public WebElement getPasswordInputElement() {
        wait.until(ExpectedConditions.visibilityOf(passwordInput));
        return passwordInput;
    }

    public WebElement getFinalSignInButtonElement() {
        wait.until(ExpectedConditions.visibilityOf(finalSignInButton));
        return finalSignInButton;
    }

    public LoginPage enterEmail(String email) {
        System.out.println("LoginPage: Entering email: " + email);
        WebElement emailField = wait.until(ExpectedConditions.visibilityOf(emailInput));
        emailField.clear();
        emailField.sendKeys(email);
        return this;
    }

    public LoginPage clickContinueSecurelyButton() {
        System.out.println("LoginPage: Clicking 'Continue Securely' button.");
        wait.until(ExpectedConditions.elementToBeClickable(continueSecurelyButton)).click();
        try {
            System.out.println("LoginPage: Waiting for password input (id=Input_Password) to be visible after clicking Continue Securely...");
            wait.until(ExpectedConditions.visibilityOf(passwordInput));
            System.out.println("LoginPage: Password input is now visible.");
        } catch (TimeoutException e) {
            System.err.println("LoginPage: Password input (id=Input_Password) did NOT become visible after clicking Continue Securely.");
        }
        return this;
    }

    public LoginPage enterPassword(String password) {
        System.out.println("LoginPage: Entering password.");
        WebElement passwordField = wait.until(ExpectedConditions.visibilityOf(passwordInput));
        passwordField.clear();
        passwordField.sendKeys(password);
        return this;
    }

    public MainPage clickFinalSignInButton() {
        System.out.println("LoginPage: Clicking final 'Sign In' button (id=loginSubmit).");

        try {
            System.out.println("LoginPage: Waiting for finalSignInButton to be VISIBLE...");
            wait.until(ExpectedConditions.visibilityOf(finalSignInButton));
            System.out.println("LoginPage: finalSignInButton is VISIBLE.");
        } catch (TimeoutException e) {
            System.err.println("LoginPage: finalSignInButton (id=loginSubmit) did NOT become visible within timeout.");
            throw e;
        }

        try {
            System.out.println("LoginPage: Waiting for finalSignInButton to be CLICKABLE...");
            wait.until(ExpectedConditions.elementToBeClickable(finalSignInButton)).click();
            System.out.println("LoginPage: Clicked final sign-in. Navigating to MainPage (logged in state)...");
        } catch (TimeoutException e) {
            System.err.println("LoginPage: finalSignInButton (id=loginSubmit) was visible BUT NOT CLICKABLE within timeout.");
            throw e;
        } catch (ElementClickInterceptedException e) {
            System.err.println("LoginPage: Click on finalSignInButton (id=loginSubmit) was INTERCEPTED.");
            throw e;
        }
        return new MainPage(driver);
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