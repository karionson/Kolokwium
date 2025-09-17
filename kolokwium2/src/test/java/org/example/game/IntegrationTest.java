package org.example.game;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class IntegrationTest {

    @Test
    public void testDuelWithOnEndCallback() {
        Player player1 = new Player();
        Player player2 = new Player();
        
        Duel duel = new Duel(player1, player2);
        
        final boolean[] callbackCalled = {false};
        
        duel.setOnEnd(() -> {
            callbackCalled[0] = true;
            
            Duel.Result result = duel.evaluate();
            assertNotNull(result, "Result should not be null when both players made different gestures");
            return result;
        });
        
        // Both players make gestures
        player1.makeGesture(Gesture.ROCK);
        assertFalse(callbackCalled[0], "Callback should not be called yet");
        
        player2.makeGesture(Gesture.SCISSORS);
        assertTrue(callbackCalled[0], "Callback should be called after both players make gestures");
    }

    @Test
    public void testDuelWithTieCallback() {
        Player player1 = new Player();
        Player player2 = new Player();
        
        Duel duel = new Duel(player1, player2);
        
        final boolean[] callbackCalled = {false};
        
        duel.setOnEnd(() -> {
            callbackCalled[0] = true;
            
            // Evaluate the result
            Duel.Result result = duel.evaluate();
            assertNull(result, "Result should be null for a tie");
            return result;
        });
        
        // Both players make the same gesture
        player1.makeGesture(Gesture.ROCK);
        player2.makeGesture(Gesture.ROCK);
        
        assertTrue(callbackCalled[0], "Callback should be called after both players make gestures");
    }

    @Test
    public void testDuelWithoutCallback() {
        Player player1 = new Player();
        Player player2 = new Player();
        
        Duel duel = new Duel(player1, player2);
        
        // Don't set callback
        player1.makeGesture(Gesture.ROCK);
        player2.makeGesture(Gesture.SCISSORS);
        
        // Should still be able to evaluate
        Duel.Result result = duel.evaluate();
        assertNotNull(result);
        assertEquals(player1, result.winner());
        assertEquals(player2, result.loser());
    }
}
