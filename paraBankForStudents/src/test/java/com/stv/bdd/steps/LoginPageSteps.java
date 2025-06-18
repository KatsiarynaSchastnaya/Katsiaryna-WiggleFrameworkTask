package com.stv.bdd.steps;

import com.stv.factory.factorypages.LoginPage;
import com.stv.factory.factorypages.MainPage; // Импорт нужен, так как мы создаем MainPage
import com.stv.framework.core.drivers.MyDriver;
// import io.cucumber.java.en.Given; // Аннотация @Given больше не используется в этом классе для этого шага
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import java.time.Duration;

public class LoginPageSteps {

    private WebDriver driver;
    private MainPage mainPage;
    private LoginPage loginPage;


    @When("The user navigates to the login page")
    public void userNavigatesToTheLoginPage() {
        if (this.driver == null) {
            this.driver = MyDriver.getDriver();
            if (this.driver == null) {
                Assert.fail("WebDriver instance is null in LoginPageSteps @When. Cannot proceed.");
            }
        }

        if (this.mainPage == null) {
            this.mainPage = new MainPage(this.driver);
        }

        loginPage = mainPage.clickSignInEntryPoint();
        Assert.assertNotNull(loginPage, "Failed to navigate to Login Page. LoginPage object is null.");

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOf(loginPage.getPageTitleElement()));
            System.out.println("BDD Step (LoginPageSteps): User has navigated to the login page - " + driver.getCurrentUrl());
        } catch (Exception e) {
            Assert.fail("Failed to confirm navigation to login page or page title not visible. Error: " + e.getMessage());
        }
    }

    @Then("The login page title should be {string}")
    public void theLoginPageTitleShouldBe(String expectedTitle) {
        Assert.assertNotNull(loginPage, "LoginPage object was not initialized for title verification. Ensure previous steps ran correctly.");
        WebElement titleElement = loginPage.getPageTitleElement();
        Assert.assertTrue(titleElement.isDisplayed(), "Login page title element is not displayed.");
        String actualTitle = titleElement.getText().trim();
        Assert.assertEquals(actualTitle, expectedTitle, "Login page title does not match.");
        System.out.println("BDD Step (LoginPageSteps): Login page title is '" + actualTitle + "' as expected.");
    }

    @Then("The login page element identified by {string} with value {string} should be visible and described as {string}")
    public void theLoginPageElementIdentifiedByWithValueShouldBeVisible(String locatorStrategy, String locatorValue, String elementDescription) {
        Assert.assertNotNull(loginPage, "LoginPage object was not initialized. Ensure previous steps ran correctly.");

        if (this.driver == null) {
            Assert.fail("WebDriver instance is null in LoginPageSteps for element visibility check.");
        }

        WebElement element;
        By byLocator;

        switch (locatorStrategy.toLowerCase()) {
            case "id":
                byLocator = By.id(locatorValue);
                break;
            case "css":
                byLocator = By.cssSelector(locatorValue);
                break;
            case "xpath":
                byLocator = By.xpath(locatorValue);
                break;
            default:
                Assert.fail("Unsupported locator strategy: " + locatorStrategy + " for element: " + elementDescription);
                return;
        }

        try {
            WebDriverWait elementWait = new WebDriverWait(driver, Duration.ofSeconds(10));
            element = elementWait.until(ExpectedConditions.visibilityOfElementLocated(byLocator));
            Assert.assertTrue(element.isDisplayed(),
                    "Element '" + elementDescription + "' (using " + locatorStrategy + "='" + locatorValue + "') is not visible, though found in DOM.");
            System.out.println("BDD Step (LoginPageSteps): Element '" + elementDescription + "' [" + locatorStrategy + "='" + locatorValue + "'] is visible.");
        } catch (TimeoutException e) {
            String errorMessage = "Element '" + elementDescription + "' (using " + locatorStrategy + "='" + locatorValue + "') was not found or not visible within timeout.";
            System.err.println(errorMessage + " Error: " + e.getMessage());
            System.err.println("Page URL at time of error: " + driver.getCurrentUrl());
            Assert.fail(errorMessage);
        } catch (Exception e) {
            String errorMessage = "An error occurred while verifying element '" + elementDescription + "' (using " + locatorStrategy + "='" + locatorValue + "').";
            System.err.println(errorMessage + " Error: " + e.getMessage());
            Assert.fail(errorMessage);
        }
    }
}