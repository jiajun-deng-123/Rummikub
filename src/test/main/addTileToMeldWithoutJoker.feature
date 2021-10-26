@tag
Feature: One player attempts to play a tile to an existed meld without joker

  @tag1
  Scenario: Player tries to play a tile to a existed run without joker
    Given Game has started
    And Player has "<tile>" in his hand
    And Player is not playing his first round
    And Table now has "<meld>" on it
    When Player played a tile "<tile>"
    Then Game outputs "<response>"
    Examples:
      |tile|meld|response|
      |R4|R6 R7 R8 R9|Please play a valid tile|
      |B6|G3 G4 G5|Please play a valid tile|
      |G6|G3 G4 G5|A tile has been added to the meld|

  @tag2
  Scenario: Player tries to play a tile to a existed set without joker
    Given Game has started
    And Player has "<tile>" in his hand
    And Player is not playing his first round
    And Table now has "<meld>" on it
    When Player played a tile "<tile>"
    Then Game outputs "<response>"
    Examples:
      |tile|meld|response|
      |B3|B6 R6 G6|Please play a valid tile|
    |B4|R4 O4 G4|A tile has been added to the meld|