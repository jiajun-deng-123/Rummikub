package main;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import junit.framework.TestCase;

import java.util.Arrays;
import java.util.LinkedList;

public class StepDefAddTile extends TestCase {
    gameServer gs;

    @Given("Player is not playing his first round")
    public void player_is_not_playing_his_first_round() {
        gs.isFirstHand[0] = false;
    }

    @Given("Table now has {string} on it")
    public void table_now_has_on_it(String string) {
        gs.shareTable = new LinkedList<meld>();
        String[] str = string.split(" ");
        String[] tiles = Arrays.stream(str)
                .filter(value ->
                        value != null && value.length() > 0
                )
                .toArray(size -> new String[size]);
        meld m = new meld();
        for (int i = 0; i < tiles.length; i++){
            m.addTile(new tile(tiles[i]));
        }
        gs.shareTable.add(m);
    }

    @When("Player played a tile {string}")
    public void player_played_a_tile(String string) {
        gs.addOneMeld(string, 0);
    }
}
