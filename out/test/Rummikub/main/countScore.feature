@tag
Feature: When one player played all his card and end the game, system will count the score and print the winner

  @tag1
  Scenario: Player played all his card and end the game
    Given Game has started
    And Player played all his card
    When System checks if game over
    Then Game End

  @tag2
  Scenario: Game end and count the score without joker
    Given Game has ended
    And Player 1 has "<t1>"
    And Player 2 has "<t2>"
    And Player 3 has "<t3>"
    When System counts the score
    Then Game outputs "<response>"
    Examples:
      |t1|t2|t3|response|
      |R4|R6 R7 B10 R9||Game over. Player 3 is the winner of the game. \nFinal Score:\nPlayer 1 (-4)\nPlayer 2 (-32)\nPlayer 3 (36)\n|
      |R5 R8 G10||B9 O11 R1|Game over. Player 2 is the winner of the game. \nFinal Score:\nPlayer 1 (-23)\nPlayer 2 (44)\nPlayer 3 (-21)\n|

