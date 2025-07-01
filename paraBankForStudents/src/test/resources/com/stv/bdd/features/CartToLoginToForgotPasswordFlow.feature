# Language: en
Feature: User flow from Cart to Forgotten Password and back

  As a user of Wiggle
  I want to navigate through the cart and login process, including the forgotten password journey,
  to ensure all pages and main functionalities are working correctly.

  @IndividualTask @CartFlow
  Scenario: Verify the full navigation path from an empty cart to forgotten password and back to the main page

    # Steps 1-3
    Given The user is on the Wiggle main page
    When The user clicks on the basket icon
    Then The user should be on the empty basket page

    # Steps 4-6
    And The basket icon should be visible in the header
    And The "Sign In" button should be visible on the empty basket page
    When The user clicks the "Sign In" button on the empty basket page

    # Step 7
    Then The user should be on the Login page

    # Steps 8-10
    When The user clicks the "Continue Securely" button on the Login page
    Then The user should still be on the Login page
    And An email error message should be displayed on the Login page

    # Steps 11-13
    When The user clicks the "Forgotten your password?" link
    Then The user should be on the Forgotten Password page
    And The "Cancel" button should be visible on the Forgotten Password page

    # Steps 14-15
    When The user clicks the "Cancel" button on the Forgotten Password page
    Then The user should be returned to the Login page

    # Steps 16-17
    When The user clicks the Wiggle logo to return to the Main page
    Then The user should be returned to the Wiggle main page