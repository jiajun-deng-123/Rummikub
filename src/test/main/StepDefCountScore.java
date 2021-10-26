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

    
}
