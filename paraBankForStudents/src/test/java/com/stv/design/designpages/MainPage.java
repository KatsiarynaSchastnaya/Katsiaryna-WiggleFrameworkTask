package com.stv.design.designpages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/* Created by tatti on 5/25/2024*/
public class MainPage extends Page {
    private final By ALLOW_ALL_BUTTON = By.cssSelector("button#onetrust-accept-btn-handler");
    private final By MAIN_LOGO = By.cssSelector("div.LogoWrap>a[title='Home page']");

    public boolean isMainLogoDisplayed() {
        WebElement registerLink = getDriver().findElement(MAIN_LOGO);
        return registerLink.isDisplayed();
    }

    public void clickOnAllowCookiesIfVisible() {
        WebElement allowCookiesButton = getDriver().findElement(ALLOW_ALL_BUTTON);
        if (allowCookiesButton.isDisplayed())
            allowCookiesButton.click();
    }
}
