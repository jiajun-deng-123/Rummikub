@tag
Feature: One player attempts to play a JR to an ex

  @tag1
  Scenario: Player tries to play a run as initial 30 with joker
    Given Game has started
    And Player has "<tiles>" in his hand
    And Player is not playing his first round
    And Table now has "<melds>" on it
    When Player played "<tile>"
    Then Game outputs "<response>"
    Examples:
      |tiles|response|
      |R7 R8 JR|insufficient total for initial tiles|
      |B1 B2 B3 JR|insufficient total for initial tiles|
      |O11 O12 JR|add meld successfully, handcard and table updated|
      |R8 R9 R11 JR|add meld successfully, handcard and table updated|