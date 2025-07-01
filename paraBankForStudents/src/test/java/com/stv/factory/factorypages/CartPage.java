package com.stv.factory.factorypages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

public class CartPage extends BasePage {

    @FindBy(xpath = "//h1[normalize-space()='Your bag is empty']")
    private WebElement emptyBasketMessage;

    @FindBy(className = "cart-page__primary-button")
    private WebElement signInButton;

    public CartPage(WebDriver driver) {
        super(driver);
        if (!isPageLoaded()) {
            Assert.fail("Cart page did not load correctly: 'Your bag is empty' message was not found.");
        }
    }

    public boolean isPageLoaded() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(emptyBasketMessage)).isDisplayed();
        } catch (Exception e) {
            System.err.println("CartPage: 'Your bag is empty' message not found. Page might not have loaded correctly. Error: " + e.getMessage());
            return false;
        }
    }

    public boolean isSignInButtonDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(signInButton)).isDisplayed();
        } catch (Exception e) {
            System.err.println("'Sign In' button not found or not visible. Check its locator: @FindBy(className = \"cart-page__primary-button\")");
            return false;
        }
    }

    public LoginPage clickSignInButton() {
        System.out.println("CartPage: Clicking 'Sign In' button...");
        try {
            wait.until(ExpectedConditions.elementToBeClickable(signInButton)).click();
            System.out.println("CartPage: 'Sign In' button clicked. Navigating to Login Page.");
            return new LoginPage(driver);
        } catch (Exception e) {
            Assert.fail("Failed to click 'Sign In' button on the cart page. Error: " + e.getMessage());
        }
        return null;
    }
}