@tag
Feature: One player attempts to play initial 30 points without using any joker

  @tag1
  Scenario: Player tries to play less than 30 points as initial run
    Given Game has started
    And Player has {<str> <str> <str>} in his hand
    And Player is playing his first round
    When Player played {<str> <str> <str>}
    Then Game outputs "insufficient total for initial tiles"
