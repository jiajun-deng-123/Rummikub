@tag
Feature: One player attempts to play a tile to an existed meld with joker

  @tag1
  Scenario: Player tries to play a tile to a existed run with joker
    Given Game has started
    And Player has "<tile>" in his hand
    And Player is not playing his first round
    And Table now has "<meld>" on it
    When Player played a tile "<tile>"
    Then Game outputs "<response>"
    Examples:
      |tile|meld|response|
      |R4|R6 R7 JR R9|Please play a valid tile|
      |B6|G3 G4 G5 JR|Please play a valid tile|
      |B6|B3 B4 B5 JR|A tile has been added to the meld|
      |G5|G3 G4 G6 JR|A tile has been added to the meld|
      |G7|G3 G4 G5 JR|A tile has been added to the meld|

  @tag2
  Scenario: Player tries to play a tile to a existed set with joker
    Given Game has started
    And Player has "<tile>" in his hand
    And Player is not playing his first round
    And Table now has "<meld>" on it
    When Player played a tile "<tile>"
    Then Game outputs "<response>"
    Examples:
      |tile|meld|response|
      |B3|B6 R6 JR|Please play a valid tile|
      |B4|R4 O4 JR|A tile has been added to the meld|