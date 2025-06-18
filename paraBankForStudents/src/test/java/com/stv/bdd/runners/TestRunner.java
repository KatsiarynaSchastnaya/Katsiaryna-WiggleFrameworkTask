package com.stv.bdd.runners;

import com.stv.framework.core.drivers.MyDriver;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

import java.time.Duration;

@CucumberOptions(
        features = "paraBankForStudents/src/test/resources/com/stv/bdd/features",
        glue = "com.stv.bdd.steps",
        tags = "not @Defect_BrandFilterProductCount",
        plugin = {
                "pretty",
                "html:target/cucumber-reports/main-cucumber-html-report.html",
                "json:target/cucumber-reports/main-cucumber.json",
                "testng:target/cucumber-reports/main-cucumber-testng.xml",
                "rerun:target/cucumber-reports/main-rerun.txt"
        }
)
public class TestRunner extends AbstractTestNGCucumberTests {

    @BeforeClass(alwaysRun = true)
    public static void setUpCucumberWebDriver() {
        System.out.println("MainTestRunner: @BeforeClass - Setting up WebDriver.");
        WebDriver driver = MyDriver.getDriver();
        if (driver != null) {
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            System.out.println("MainTestRunner: WebDriver setup complete for the class. Implicit wait set to 10 seconds.");
        } else {
            System.err.println("MainTestRunner: WebDriver instance was NULL during setUpCucumberWebDriver. Tests might fail.");
        }
    }

    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }

    @AfterClass(alwaysRun = true)
    public static void tearDownCucumberWebDriver() {
        System.out.println("MainTestRunner: @AfterClass - Tearing down WebDriver.");
        MyDriver.quitDriver();
        System.out.println("MainTestRunner: WebDriver quit process initiated via MyDriver.");
    }
}