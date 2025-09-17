package org.example.game;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GestureTest {

    @Test
    public void testFromString() {
        // Test valid conversions
        assertEquals(Gesture.ROCK, Gesture.fromString("r"));
        assertEquals(Gesture.PAPER, Gesture.fromString("p"));
        assertEquals(Gesture.SCISSORS, Gesture.fromString("s"));
        
        // Test case insensitivity
        assertEquals(Gesture.ROCK, Gesture.fromString("R"));
        assertEquals(Gesture.PAPER, Gesture.fromString("P"));
        assertEquals(Gesture.SCISSORS, Gesture.fromString("S"));
    }

    @Test
    public void testFromStringInvalidInput() {
        // Test invalid inputs
        assertThrows(IllegalArgumentException.class, () -> Gesture.fromString("x"));
        assertThrows(IllegalArgumentException.class, () -> Gesture.fromString("rock"));
        assertThrows(IllegalArgumentException.class, () -> Gesture.fromString(""));
        assertThrows(IllegalArgumentException.class, () -> Gesture.fromString(null));
    }

    @Test
    public void testCompareWithTie() {
        // Test ties
        assertEquals(0, Gesture.ROCK.compareWith(Gesture.ROCK));
        assertEquals(0, Gesture.PAPER.compareWith(Gesture.PAPER));
        assertEquals(0, Gesture.SCISSORS.compareWith(Gesture.SCISSORS));
    }

    @Test
    public void testCompareWithWins() {
        // Test winning scenarios
        assertEquals(1, Gesture.ROCK.compareWith(Gesture.SCISSORS)); // ROCK beats SCISSORS
        assertEquals(1, Gesture.PAPER.compareWith(Gesture.ROCK)); // PAPER beats ROCK
        assertEquals(1, Gesture.SCISSORS.compareWith(Gesture.PAPER)); // SCISSORS beats PAPER
    }

    @Test
    public void testCompareWithLoses() {
        // Test losing scenarios
        assertEquals(-1, Gesture.ROCK.compareWith(Gesture.PAPER)); // ROCK loses to PAPER
        assertEquals(-1, Gesture.PAPER.compareWith(Gesture.SCISSORS)); // PAPER loses to SCISSORS
        assertEquals(-1, Gesture.SCISSORS.compareWith(Gesture.ROCK)); // SCISSORS loses to ROCK
    }
}
