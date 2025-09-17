package org.example.game;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DuelTest {

    @Test
    public void testIsDuelling() {
        // Create two players
        Player player1 = new Player();
        Player player2 = new Player();
        
        // Initially, players should not be duelling
        assertFalse(player1.isDuelling(), "Player1 should not be duelling initially");
        assertFalse(player2.isDuelling(), "Player2 should not be duelling initially");
        
        // Create a duel with these players
        Duel duel = new Duel(player1, player2);
        
        // After creating duel, both players should be duelling
        assertTrue(player1.isDuelling(), "Player1 should be duelling after entering duel");
        assertTrue(player2.isDuelling(), "Player2 should be duelling after entering duel");
    }

    @Test
    public void testEvaluatePlayerWins() {
        // Create two players and a duel
        Player player1 = new Player();
        Player player2 = new Player();
        Duel duel = new Duel(player1, player2);
        
        // Player1 plays ROCK, Player2 plays SCISSORS
        // ROCK should beat SCISSORS
        player1.makeGesture(Gesture.ROCK);
        player2.makeGesture(Gesture.SCISSORS);
        
        // Evaluate the duel
        Duel.Result result = duel.evaluate();
        
        // Player1 should win
        assertNotNull(result, "Result should not be null when there's a winner");
        assertEquals(player1, result.winner(), "Player1 should be the winner");
        assertEquals(player2, result.loser(), "Player2 should be the loser");
    }

    @Test
    public void testEvaluateTie() {
        // Create two players and a duel
        Player player1 = new Player();
        Player player2 = new Player();
        Duel duel = new Duel(player1, player2);
        
        // Both players play the same gesture
        player1.makeGesture(Gesture.ROCK);
        player2.makeGesture(Gesture.ROCK);
        
        // Evaluate the duel
        Duel.Result result = duel.evaluate();
        
        // Result should be null for a tie
        assertNull(result, "Result should be null when there's a tie");
    }

    @Test
    public void testLeaveDuel() {
        // Create two players and a duel
        Player player1 = new Player();
        Player player2 = new Player();
        Duel duel = new Duel(player1, player2);
        
        // Both players should be duelling
        assertTrue(player1.isDuelling());
        assertTrue(player2.isDuelling());
        
        // Player1 leaves the duel
        player1.leaveDuel();
        
        // Player1 should no longer be duelling
        assertFalse(player1.isDuelling(), "Player1 should not be duelling after leaving");
        // Player2 should still be duelling
        assertTrue(player2.isDuelling(), "Player2 should still be duelling");
    }
}
