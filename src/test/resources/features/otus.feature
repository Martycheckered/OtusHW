Feature: Testing Otus site elements

  Scenario: Check Otus address
    Given Otus site is open in browser
    When Contacts button is clicked
    Then Address information is correct

  Scenario: Check title of Otus contact page
    Given Otus site is open in browser
    When Contacts button is clicked
    Then Title name is correct