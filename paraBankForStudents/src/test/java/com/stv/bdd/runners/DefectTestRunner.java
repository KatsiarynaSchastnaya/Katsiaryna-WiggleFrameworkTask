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
        tags = "@Defect_BrandFilterProductCount",
        plugin = {
                "pretty",
                "html:target/cucumber-reports/defect-cucumber-html-report.html",
                "json:target/cucumber-reports/defect-cucumber.json",
                "testng:target/cucumber-reports/defect-cucumber-testng.xml",
                "rerun:target/cucumber-reports/defect-rerun.txt"
        }
)
public class DefectTestRunner extends AbstractTestNGCucumberTests {

    @BeforeClass(alwaysRun = true)
    public static void setUpDefectTestWebDriver() {
        System.out.println("DefectTestRunner: @BeforeClass - Setting up WebDriver for defect test.");
        WebDriver driver = MyDriver.getDriver();
        if (driver != null) {
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            System.out.println("DefectTestRunner: WebDriver setup complete.");
        } else {
            System.err.println("DefectTestRunner: WebDriver instance was NULL. Defect test might fail prematurely.");
        }
    }

    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }

    @AfterClass(alwaysRun = true)
    public static void tearDownDefectTestWebDriver() {
        System.out.println("DefectTestRunner: @AfterClass - Tearing down WebDriver after defect test.");
        MyDriver.quitDriver();
        System.out.println("DefectTestRunner: WebDriver quit complete.");
    }
}