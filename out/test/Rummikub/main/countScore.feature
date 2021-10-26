@tag
Feature: When one player played all his card and end the game, system will count the score and print the winner

  @tag1
  Scenario: Player played all his card and end the game
    Given Game has started
    And Player played all his card
    When System checks if game over
    Then Game End