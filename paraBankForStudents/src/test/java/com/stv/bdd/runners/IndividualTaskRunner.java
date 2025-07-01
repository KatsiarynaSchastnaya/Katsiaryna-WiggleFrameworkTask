package com.stv.bdd.runner;

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
        tags = "@IndividualTask",
        plugin = {
                "pretty",
                "html:target/cucumber-reports/individual-task-report.html",
                "json:target/cucumber-reports/individual-task.json",
                "testng:target/cucumber-reports/individual-task-testng.xml",
                "rerun:target/cucumber-reports/individual-task-rerun.txt"
        }
)
public class IndividualTaskRunner extends AbstractTestNGCucumberTests {

    @BeforeClass(alwaysRun = true)
    public static void setUpIndividualTaskWebDriver() {
        System.out.println("IndividualTaskRunner: @BeforeClass - Setting up WebDriver for the individual task.");
        WebDriver driver = MyDriver.getDriver();
        if (driver != null) {
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            System.out.println("IndividualTaskRunner: WebDriver setup complete.");
        } else {
            System.err.println("IndividualTaskRunner: WebDriver instance was NULL. The test will likely fail.");

        }
    }

    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }

    @AfterClass(alwaysRun = true)
    public static void tearDownIndividualTaskWebDriver() {
        System.out.println("IndividualTaskRunner: @AfterClass - Tearing down WebDriver after the individual task.");
        MyDriver.quitDriver();
        System.out.println("IndividualTaskRunner: WebDriver quit complete.");
    }
}