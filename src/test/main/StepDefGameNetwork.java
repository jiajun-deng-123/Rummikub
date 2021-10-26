package main;


import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.plugin.event.TestCase;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class StepDefGameNetwork {
    gameServer gs;
    //myClient[] mc = new myClient[3];

    @Given("Server is on")
    public void server_is_on(){

    }

    @Given("Player connects to server at port {int}")
    public void player_connects_to_server_at_port(Integer int1) {
        
    }

    @When("Player starts the game")
    public void player_starts_the_game() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("Player receives player id")
    public void player_receives_player_id() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("Player receives their handcard and sharing table")
    public void player_receives_their_handcard_and_sharing_table() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

}
