package com.stv.factory.factorytests;

import com.stv.framework.core.drivers.MyDriver;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import java.time.Duration;

public class BaseFactoryTest {
    protected WebDriver driver;

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        System.out.println("BaseFactoryTest: setUp() - Requesting WebDriver instance from MyDriver...");
        driver = MyDriver.getDriver();
        System.out.println("BaseFactoryTest: WebDriver instance received. Maximizing window and setting implicit wait.");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        System.out.println("BaseFactoryTest: setUp() complete.");
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        System.out.println("BaseFactoryTest: tearDown() - Requesting to quit WebDriver instance via MyDriver...");
        MyDriver.quitDriver();
        System.out.println("BaseFactoryTest: tearDown() complete.");
    }
}