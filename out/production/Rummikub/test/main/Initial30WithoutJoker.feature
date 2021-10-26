@tag
Feature: One player attempts to play initial 30 points without using any joker

  @tag1
  Scenario: Player tries to play a run as initial 30
    Given Game has started
    And Player has "<tiles>" in his hand
    And Player is playing his first round
    When Player played "<tiles>"
    Then Game outputs "<response>"
    Examples:
    |tiles|response|
    |R7 R8 R9|insufficient total for initial tiles|
    |B1 B2 B3|insufficient total for initial tiles|
    |O11 O12 O13|add meld successfully, handcard and table updated|
    |R8 R9 R10 R11 |add meld successfully, handcard and table updated|

  @tag2
  Scenario: Player tries to play a set as initial 30
    Given Game has started
    And Player has "<tiles>" in his hand
    And Player is playing his first round
    When Player played "<tiles>"
    Then Game outputs "<response>"
    Examples:
      |tiles    | response|
      |R4 B4 O4|insufficient total for initial tiles|
      |G1 B1 R1|insufficient total for initial tiles|
      |O11 B11 G11|add meld successfully, handcard and table updated|
      |R8 O8 G8 B8 |add meld successfully, handcard and table updated|
