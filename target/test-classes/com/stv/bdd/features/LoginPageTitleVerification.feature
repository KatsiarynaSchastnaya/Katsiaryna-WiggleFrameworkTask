# Language: en
Feature: Login Page Title Verification
  As a user of Wiggle
  I want to ensure the login page has the correct title
  So that I know I am on the correct page

  Background:
    Given The user is on the Wiggle main page
    When The user navigates to the login page

  @LoginSmokeTest @SimpleScenario
  Scenario: Verify the main title of the login page
    Then The login page title should be "Sign In Or Register"