package main;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import junit.framework.TestCase;

import java.util.Arrays;
import java.util.LinkedList;

public class StepDefCountScore extends TestCase {
    gameServer gs;

    @Given("Player played all his card")
    public void player_played_all_his_card() {
        gs.handcard.get(gs.handindex).clear();
    }

    @When("System checks if game over")
    public void system_checks_if_game_over() {
        gs.checkEnd();
    }

    @Then("Game End")
    public void game_End() {
        assertTrue(gs.isEnd);
    }

    @Given("Game has ended")
    public void game_has_ended() {
        gs.playerCount = 3;
        gs.handcard = new LinkedList<LinkedList<tile>>();
        for (int i = 0; i < 3; i++){
            gs.handcard.add(new LinkedList<tile>());
        }
        gs.isEnd = true;
    }

    @Given("Player {int} has {string}")
    public void player_has(Integer int1, String string) {
        String[] str = string.split(" ");
        String[] tiles = Arrays.stream(str)
                .filter(value ->
                        value != null && value.length() > 0
                )
                .toArray(size -> new String[size]);
        for (int i = 0; i < tiles.length; i++){
            gs.getHand(int1 - 1).add(new tile(tiles[i]));
        }
    }


    @When("System counts the score")
    public void system_counts_the_score() {
        gs.countScore();
    }
}
