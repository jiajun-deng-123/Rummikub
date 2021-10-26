@tag
Feature: Test features for an end to end game
  I want to use this to test functions for an end to end game

  @tag1
  Scenario: Play an entire game
    Given Server is on
    And Player connects to server at port 30000
    When Player plays a turn
    Then Player receives their handcard and sharing table