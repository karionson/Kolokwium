package game;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;

import static org.junit.jupiter.api.Assertions.*;

class DuelTest {
        @Test
        public void testIfPlayersAreInDuel() {
            Player player1 = new Player();
            Player player2 = new Player();
            assertEquals(false, player1.isDuelling(), "Gracz 1 nie jest w pojedynku");
            assertEquals(false, player2.isDuelling(), "Gracz 1 nie jest w pojedynku");
            Duel duel = new Duel(player1, player2);
            assertEquals(true, player1.isDuelling(), "Gracz 1 jest w pojedynku");
            assertEquals(true, player2.isDuelling(), "Gracz 2 jest w pojedynku");

        }

    @Test
    void evaluate() {
        Player player1 = new Player();
        Player player2 = new Player();
        Duel duel = new Duel(player1, player2);
        duel.handleGesture(player1, Gesture.ROCK, player2, Gesture.PAPER);
        assertEquals(duel.evaluate().winner(), player2);
    }

}