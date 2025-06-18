# Language: en
Feature: Login Page Initial Screen Elements Visibility (Outline)
  As a user of Wiggle
  I want to ensure key elements are present and visible on the initial screen of the login page
  So that I can confirm the page has loaded correctly before entering my email

  Background:
    Given The user is on the Wiggle main page
    When The user navigates to the login page

  @LoginScreenElements @VisibilityCheckOutline
  Scenario Outline: Verify visibility of key elements on the initial login screen
    Then The login page element identified by "<LocatorStrategy>" with value "<LocatorValue>" should be visible and described as "<ElementDescription>"

    Examples: Initial Login Screen Elements
      | LocatorStrategy | LocatorValue                                                                        | ElementDescription         |
      | xpath           | //h2[normalize-space()='Sign in or Register']                                       | Page Title                 |
      | id              | Input_EmailAddress                                                                  | Email Input Field          |
      | id              | emailSubmit                                                                         | Continue Securely Button   |
      | xpath           | //a[contains(@class,'forgot-password') and normalize-space()='Forgotten your password?'] | Forgotten Password Link    |