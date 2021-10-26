@tag
Feature: Ending the game
  I want to use this to test ending the game

  @gameEnds
  Scenario: Game ends
    Given Server is on
    And Player connects to server
    When One of player's handcard is all played
    Then Player receives winner and the score