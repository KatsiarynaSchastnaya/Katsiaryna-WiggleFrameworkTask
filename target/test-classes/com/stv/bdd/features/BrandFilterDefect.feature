# Language: en
Feature: Brand Filter Interaction and Product Count Consistency on Run Page

  As a user of Wiggle
  I want to apply price and brand filters on the "Run" category page
  I expect the product count display for other unaffected brands to remain consistent or update correctly

  Background:
    Given The user is on the Wiggle main page
    When The user navigates to the "Run" category page

  # Тег для запуска этого сценария через отдельный DefectTestRunner
  @Defect_BrandFilterProductCount
  Scenario: Verify product count for New Balance remains consistent after applying Adidas filter with specific price range
    When The user applies a price filter for range "£250 to £500"
    And The user observes the initial product count for brand "New Balance"
    When The user selects the brand "adidas"
    Then The product count for brand "New Balance" should remain visible and be the same as the initial count