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
        } catch (TimeoutException | NoSuchElementException e) {

        } catch (Exception e) {
            System.err.println("MainPage: Error handling cookie banner: " + e.getMessage());
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

        } catch (TimeoutException e) {
            System.out.println("MainPage: TimeoutException while checking login state.");
            System.out.println("TimeoutException details: " + e.getMessage());
            return false;
        } catch (NoSuchElementException nse) {
            System.out.println("MainPage: NoSuchElementException while checking login state.");
            System.out.println("NoSuchElementException details: " + nse.getMessage());
            return false;
        }
        catch (Exception generalException) {
            System.err.println("MainPage: An unexpected error occurred while checking login state: " + generalException.getMessage());
            generalException.printStackTrace();
            return false;
        }
    }


    public ProductListingPage navigateToCategory(String categoryName) {
        System.out.println("MainPage: Attempting to navigate to category: " + categoryName);
        WebElement categoryLinkElement = null;

        if ("Run".equalsIgnoreCase(categoryName)) {
            categoryLinkElement = this.runCategoryLink;
        }

        else {
            String genericCategoryXPath = String.format("//ul[@class='primary-nav__list']//a[contains(@href,'/%s') and (normalize-space()='%s' or .//span[normalize-space()='%s'])]",
                    categoryName.toLowerCase(), categoryName, categoryName);
            try {
                System.out.println("MainPage: Trying generic XPath for category '" + categoryName + "': " + genericCategoryXPath);
                categoryLinkElement = driver.findElement(By.xpath(genericCategoryXPath));
            } catch (NoSuchElementException e) {
                System.err.println("MainPage: Category '" + categoryName + "' not found using predefined locators or generic XPath.");
                Assert.fail("Navigation to category '" + categoryName + "' is not implemented or category link not found.");
            }
        }

        try {
            Assert.assertNotNull(categoryLinkElement, "Category link element for '" + categoryName + "' is null. Check locator logic.");
            System.out.println("MainPage: Waiting for category link '" + categoryName + "' to be clickable.");

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", categoryLinkElement);
            wait.until(ExpectedConditions.elementToBeClickable(categoryLinkElement)).click();
            System.out.println("MainPage: Clicked on '" + categoryName + "' category link.");

            return new ProductListingPage(driver);
        } catch (TimeoutException e) {
            String errorMessage = "Category link for '" + categoryName + "' not found or not clickable on Main Page within timeout.";
            System.err.println(errorMessage);
            Assert.fail(errorMessage + " Details: " + e.getMessage());
        } catch (NullPointerException e) {
            String errorMessage = "NullPointerException while trying to click category link for: " + categoryName + ". CategoryLinkElement might be null.";
            System.err.println(errorMessage);
            Assert.fail(errorMessage);
        }
        return null;
    }

}