package com.stv.bdd.steps;

import com.stv.factory.factorypages.MainPage;
import com.stv.framework.core.drivers.MyDriver;
import io.cucumber.java.en.Given;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class CommonSteps {

    private WebDriver driver;
    private MainPage mainPage;

    @Given("The user is on the Wiggle main page")
    public void userIsOnTheWiggleMainPage() {

        if (this.driver == null) {
            this.driver = MyDriver.getDriver();
            if (this.driver == null) {
                Assert.fail("WebDriver instance is null in CommonSteps @Given. Cannot proceed.");
            }
        }
        mainPage = new MainPage(this.driver);
        mainPage.open();
        System.out.println("BDD Step (CommonSteps): User is on the Wiggle main page - " + this.driver.getCurrentUrl());
    }

}