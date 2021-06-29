Feature: Testing Tele2 site elements

  Scenario: Check search on site
    Given Tele2 site is open in browser
    When User enters "97" in search field
    Then Output telephone number contains "97"