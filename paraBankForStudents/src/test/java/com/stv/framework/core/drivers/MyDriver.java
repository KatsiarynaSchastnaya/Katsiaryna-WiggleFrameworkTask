package com.stv.framework.core.drivers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.Arrays;

public class MyDriver {
    private static WebDriver driver;

    private static final String BROWSER_NAME_PROPERTY = System.getProperty("browser", "chrome");

    public static WebDriver getDriver() {
        if (driver == null) {
            String browserName = BROWSER_NAME_PROPERTY.toLowerCase();
            switch (browserName) {
                case "firefox":
                    setupFirefoxDriverWithOptions();
                    break;
                case "chrome":
                default:
                    setupChromeDriverWithOptions();
                    break;
            }
            System.out.println(browserName.toUpperCase() + " WebDriver instance created.");
        }
        return driver;
    }

    private static void setupChromeDriverWithOptions() {
        System.out.println("Setting up ChromeDriver using WebDriverManager...");
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();

        options.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));
        options.setExperimentalOption("useAutomationExtension", false);



        options.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/136.0.0.0 Safari/537.36");


        System.out.println("Initializing ChromeDriver with options...");
        driver = new ChromeDriver(options);
    }

    private static void setupFirefoxDriverWithOptions() {
        System.out.println("Setting up FirefoxDriver using WebDriverManager...");
        WebDriverManager.firefoxdriver().setup();

        driver = new FirefoxDriver(/*options*/);
    }

    public static void quitDriver() {
        if (driver != null) {
            System.out.println("Quitting WebDriver instance...");
            driver.quit();
            driver = null;
            System.out.println("WebDriver instance quit and set to null.");
        }
    }
}
