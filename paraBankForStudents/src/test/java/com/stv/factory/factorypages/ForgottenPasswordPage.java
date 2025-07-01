package com.stv.factory.factorypages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class ForgottenPasswordPage extends BasePage {

    @FindBy(xpath = "//h2[normalize-space()='Create a new password']")
    private WebElement pageTitle;

    @FindBy(id = "forgottenPasswordSubmit")
    private WebElement sendEmailButton;

    @FindBy(id = "Input_EmailAddress-error")
    private WebElement emailErrorMessage;

    @FindBy(id = "forgotPasswordCancel")
    private WebElement cancelButton;

    public ForgottenPasswordPage(WebDriver driver) {
        super(driver);
        switchToCorrectIframeContainingForm();
    }


    private void switchToCorrectIframeContainingForm() {
        System.out.println("ForgottenPasswordPage: Searching for the correct iframe...");
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("iframe")));

            List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
            System.out.println("ForgottenPasswordPage: Found " + iframes.size() + " iframe(s). Checking each one.");

            boolean formFoundInIframe = false;
            for (int i = 0; i < iframes.size(); i++) {
                try {
                    driver.switchTo().frame(i);
                    System.out.println("ForgottenPasswordPage: Switched to iframe index " + i);

                    new WebDriverWait(driver, Duration.ofSeconds(1))
                            .until(ExpectedConditions.visibilityOf(pageTitle));

                    System.out.println("ForgottenPasswordPage: Success! Found form title in iframe index " + i + ".");
                    formFoundInIframe = true;
                    break;

                } catch (Exception e) {
                    System.out.println("ForgottenPasswordPage: Title not found in iframe index " + i + ". Trying next one.");
                    driver.switchTo().defaultContent();
                }
            }

            if (!formFoundInIframe) {
                driver.switchTo().defaultContent();
                System.out.println("ForgottenPasswordPage: Form not found in any iframe. Checking default content...");

                wait.until(ExpectedConditions.visibilityOf(pageTitle));
                System.out.println("ForgottenPasswordPage: Warning! Found title in default content, not in an iframe. Check page structure.");
            }

        } catch (Exception e) {
            Assert.fail("Could not find the form on Forgotten Password page, neither in an iframe nor in the default content. Error: " + e.getMessage());
        }
    }


    private void switchToDefaultContent() {
        try {
            driver.switchTo().defaultContent();
        } catch (Exception e) {
            System.err.println("Could not switch back to default content: " + e.getMessage());
        }
    }

    public boolean isPageTitleDisplayed() {
        try {
            return pageTitle.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isSendEmailButtonDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(sendEmailButton)).isDisplayed();
        } catch (Exception e) {
            System.err.println("'Send email' button not found or not visible. Locator: @FindBy(id = \"forgottenPasswordSubmit\")");
            return false;
        }
    }

    public void clickSendEmailButton() {
        System.out.println("ForgottenPasswordPage: Clicking 'Send email' button...");
        try {
            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(sendEmailButton));
            button.click();
        } catch (Exception e) {
            System.err.println("Standard click failed for Send Email button. Trying JS click. Error: " + e.getMessage());
            try {
                WebElement button = driver.findElement(By.id("forgottenPasswordSubmit"));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
            } catch (Exception jsException) {
                Assert.fail("Failed to click 'Send email' button with both standard and JavaScript clicks. Error: " + jsException.getMessage());
            }
        }
    }

    public boolean isEmailErrorMessageDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(emailErrorMessage)).isDisplayed();
        } catch (Exception e) {
            System.err.println("Email error message not found on Forgotten Password page.");
            return false;
        }
    }

    public boolean isCancelButtonDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(cancelButton)).isDisplayed();
        } catch (Exception e) {
            System.err.println("'Cancel' button not found or not visible. Check locator: @FindBy(id = \"forgottenPasswordCancel\")");
            return false;
        }
    }

    public LoginPage clickCancelButton() {
        System.out.println("ForgottenPasswordPage: Clicking 'Cancel' button...");
        try {
            wait.until(ExpectedConditions.elementToBeClickable(cancelButton)).click();
            System.out.println("ForgottenPasswordPage: 'Cancel' button clicked.");

            switchToDefaultContent();

            System.out.println("ForgottenPasswordPage: Navigating back to Login Page.");
            return new LoginPage(driver);
        } catch (Exception e) {
            Assert.fail("Failed to click 'Cancel' button. Error: " + e.getMessage());
        }
        return null;
    }
}