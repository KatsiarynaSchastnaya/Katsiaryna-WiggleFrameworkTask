package com.stv.factory.factorypages;

import com.stv.framework.core.lib.WigglePageURLs;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class MainPage extends BasePage {


    @FindBy(id = "loginMenu")
    private WebElement signInEntryPointElement;

    @FindBy(css = "span.ico")
    private WebElement userAccountHoverIcon;

    @FindBy(xpath = "//span[contains(@class,'logoutTxt') and normalize-space(text())='My Account']")
    private WebElement myAccountLinkText;

    @FindBy(id = "onetrust-accept-btn-handler")
    private WebElement allowAllCookiesButton;

    @FindBy(id = "lnkTopLevelMenu_5161006")
    private WebElement runCategoryLink;

    @FindBy(id = "aBagLink")
    private WebElement basketIconLink;



    public MainPage(WebDriver driver) {
        super(driver);
        System.out.println("MainPage instance created. Current URL: " + driver.getCurrentUrl());
    }



    public MainPage open() {
        driver.get(WigglePageURLs.START_URL);
        System.out.println("MainPage: Opened main page - " + WigglePageURLs.START_URL);
        handleCookieBannerOnMainPage();
        return this;
    }

    private void handleCookieBannerOnMainPage() {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement acceptButton = shortWait.until(ExpectedConditions.elementToBeClickable(allowAllCookiesButton));
            System.out.println("MainPage: Cookie banner found. Clicking 'ALLOW ALL'.");
            acceptButton.click();
        } catch (Exception e) {

        }
    }

    public LoginPage clickSignInEntryPoint() {
        System.out.println("MainPage: Attempting to click sign-in entry point element...");
        try {
            wait.until(ExpectedConditions.elementToBeClickable(signInEntryPointElement)).click();
            System.out.println("MainPage: Clicked sign-in entry point element. Creating LoginPage object...");
        } catch (Exception e) {
            System.err.println("MainPage: Failed to click sign-in entry point element: " + e.getMessage());
            throw e;
        }
        return new LoginPage(driver);
    }

    public CartPage clickBasketIcon() {
        System.out.println("MainPage: Attempting to click basket icon...");
        try {
            wait.until(ExpectedConditions.elementToBeClickable(basketIconLink)).click();
            System.out.println("MainPage: Clicked basket icon. Navigating to Cart Page.");
            return new CartPage(driver);
        } catch (Exception e) {
            Assert.fail("Basket icon link was not found or not clickable. Error: " + e.getMessage());
        }
        return null;
    }

    public ProductListingPage navigateToCategory(String categoryName) {
        System.out.println("MainPage: Attempting to navigate to category: " + categoryName);
        WebElement categoryLinkElement = null;

        if ("Run".equalsIgnoreCase(categoryName)) {
            categoryLinkElement = this.runCategoryLink;
        } else {

            Assert.fail("Navigation to category '" + categoryName + "' is not implemented in MainPage.java.");
        }

        try {
            Assert.assertNotNull(categoryLinkElement, "Category link element is null for " + categoryName + ". Check @FindBy locator.");
            System.out.println("MainPage: Waiting for category link '" + categoryName + "' to be clickable.");
            wait.until(ExpectedConditions.elementToBeClickable(categoryLinkElement)).click();
            System.out.println("MainPage: Clicked on '" + categoryName + "' category link.");
            return new ProductListingPage(driver);
        } catch (Exception e) {
            Assert.fail("Failed to navigate to category '" + categoryName + "'. Details: " + e.getMessage());
        }
        return null;
    }



    public boolean isUserLoggedIn() {
        System.out.println("MainPage: Checking if user is logged in...");
        try {
            System.out.println("MainPage: Waiting for userAccountHoverIcon (css='span.ico') to be visible...");
            WebDriverWait extendedWait = new WebDriverWait(driver, Duration.ofSeconds(15));
            extendedWait.until(ExpectedConditions.visibilityOf(userAccountHoverIcon));
            System.out.println("MainPage: User account hover icon (css='span.ico') IS VISIBLE.");

            Actions actions = new Actions(driver);
            System.out.println("MainPage: Performing hover over userAccountHoverIcon (css='span.ico').");
            actions.moveToElement(userAccountHoverIcon).perform();
            System.out.println("MainPage: Hover performed.");

            System.out.println("MainPage: Waiting for 'myAccountLinkText' to be visible after hover...");
            wait.until(ExpectedConditions.visibilityOf(myAccountLinkText));
            System.out.println("MainPage: 'My Account' link text IS VISIBLE after hover. User is considered logged in.");
            return myAccountLinkText.isDisplayed();
        } catch (Exception e) {
            System.out.println("Could not confirm logged-in state. Details: " + e.getMessage());
            return false;
        }
    }

    public boolean isSignInIconDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(signInEntryPointElement)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}