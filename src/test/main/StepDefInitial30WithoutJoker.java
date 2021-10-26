package main;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import junit.framework.TestCase;

import java.util.Arrays;
import java.util.LinkedList;

public class StepDefInitial30WithoutJoker extends TestCase {
    gameServer gs;



    @Given("Game has started")
    public void game_has_started() {
        gs = new gameServer();
        gs.playerCount = 1;
        gs.handcard.add(new LinkedList<tile>());
    }

    @Given("Player has {string} in his hand")
    public void player_has_in_his_hand(String string) {
        String[] str = string.split(" ");
        String[] tiles = Arrays.stream(str)
                .filter(value ->
                        value != null && value.length() > 0
                )
                .toArray(size -> new String[size]);
        for (int i = 0; i < tiles.length; i++){
            gs.getHand(0).add(new tile(tiles[i]));
        }
    }

    @Given("Player is playing his first round")
    public void player_is_playing_his_first_round() {
        gs.isFirstHand[0] = true;
    }

    @When("Player played {string}")
    public void player_played(String string) {
        gs.addNewMeld(string);
    }

    @Then("Game outputs {string}")
    public void game_outputs(String string) {
        assertEquals(string, gs.content);
    }
}
