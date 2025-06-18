package com.stv.bdd.steps;

import com.stv.factory.factorypages.MainPage;
import com.stv.factory.factorypages.ProductListingPage;
import com.stv.framework.core.drivers.MyDriver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class ProductFilterSteps {

    private WebDriver driver;
    private MainPage mainPage;
    private ProductListingPage productListingPage;
    private String initialBrandProductCount = "";

    @When("The user navigates to the {string} category page")
    public void userNavigatesToCategoryPage(String categoryName) {
        if (this.driver == null) {
            this.driver = MyDriver.getDriver();
            if (this.driver == null) {
                Assert.fail("WebDriver instance is null in ProductFilterSteps @When. Cannot proceed.");
            }
        }

        if (this.mainPage == null) {
            this.mainPage = new MainPage(this.driver);
        }

        productListingPage = mainPage.navigateToCategory(categoryName);
        Assert.assertNotNull(productListingPage, "Failed to navigate to '" + categoryName + "' page. ProductListingPage is null.");
        System.out.println("BDD Step (ProductFilterSteps): User has navigated to the '" + categoryName + "' category page.");
    }

    @When("The user applies a price filter for range {string}")
    public void userAppliesPriceFilterForRange(String priceRange) {
        Assert.assertNotNull(productListingPage, "ProductListingPage is not initialized. Cannot apply price filter.");
        productListingPage.applyPriceFilter(priceRange);
        System.out.println("BDD Step (ProductFilterSteps): Applied price filter for range: " + priceRange);
        productListingPage.waitForFilterResultsToUpdate();
    }

    @And("The user observes the initial product count for brand {string}")
    public void userObservesInitialProductCountForBrand(String brandName) {
        Assert.assertNotNull(productListingPage, "ProductListingPage is not initialized. Cannot observe product count.");
        initialBrandProductCount = productListingPage.getProductCountForBrand(brandName);
        System.out.println("BDD Step (ProductFilterSteps): Observed initial product count for brand '" + brandName + "' is: '" + initialBrandProductCount + "'");

        boolean initialCountIsDeterminedAndNumeric = !(initialBrandProductCount.equals("NOT_FOUND") ||
                initialBrandProductCount.equals("BRAND_LABEL_NOT_DISPLAYED") ||
                initialBrandProductCount.equals("COUNT_SPAN_NOT_FOUND_OR_DISPLAYED") ||
                initialBrandProductCount.equals("COUNT_SPAN_NOT_DISPLAYED") ||
                initialBrandProductCount.equals("0_EMPTY"));

        Assert.assertTrue(initialCountIsDeterminedAndNumeric,
                "Initial product count for brand '" + brandName + "' could not be determined or was effectively zero/empty ('" + initialBrandProductCount + "'). Test cannot reliably proceed with comparison.");
        try {
            Integer.parseInt(initialBrandProductCount);
        } catch (NumberFormatException e) {
            Assert.fail("Initial product count for brand '" + brandName + "' ('" + initialBrandProductCount + "') is not a valid parseable number.");
        }
        System.out.println("BDD Step (ProductFilterSteps): Initial product count for '" + brandName + "' is valid: '" + initialBrandProductCount + "'");
    }

    @When("The user selects the brand {string}")
    public void userSelectsBrand(String brandName) {
        Assert.assertNotNull(productListingPage, "ProductListingPage is not initialized. Cannot select brand.");
        productListingPage.selectBrand(brandName);
        System.out.println("BDD Step (ProductFilterSteps): Selected brand: " + brandName);
        productListingPage.waitForFilterResultsToUpdate();
    }

    @Then("The product count for brand {string} should remain visible and be the same as the initial count")
    public void productCountForBrandShouldRemainVisibleAndBeSameAsInitial(String brandName) {
        Assert.assertNotNull(productListingPage, "ProductListingPage is not initialized. Cannot verify product count.");
        String currentCountOrState = productListingPage.getProductCountForBrand(brandName);
        System.out.println("BDD Step (ProductFilterSteps): Verifying expected behavior for product count of brand '" + brandName + "'.");
        System.out.println("Initial count was: '" + initialBrandProductCount + "'. Current count/state is: '" + currentCountOrState + "'.");

        boolean isCurrentCountValidAndVisible = !(currentCountOrState.equals("NOT_FOUND") ||
                currentCountOrState.equals("BRAND_LABEL_NOT_DISPLAYED") ||
                currentCountOrState.equals("COUNT_SPAN_NOT_FOUND_OR_DISPLAYED") ||
                currentCountOrState.equals("COUNT_SPAN_NOT_DISPLAYED") ||
                currentCountOrState.equals("0_EMPTY"));

        Assert.assertTrue(isCurrentCountValidAndVisible,
                "Product count for brand '" + brandName + "' is currently missing or effectively zero/empty (state: '" + currentCountOrState + "'). Expected it to be visible and match initial count of '" + initialBrandProductCount + "'. This indicates the defect might be present.");

        Assert.assertEquals(currentCountOrState, initialBrandProductCount,
                "Product count for brand '" + brandName + "' has changed or is incorrect. Expected: '" + initialBrandProductCount + "', Actual: '" + currentCountOrState + "'. This indicates a deviation from expected behavior (defect might be present or logic changed).");

        System.out.println("BDD Step (ProductFilterSteps): Product count for brand '" + brandName + "' is visible and correctly matches initial count: '" + currentCountOrState + "'.");
    }
}