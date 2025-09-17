package org.example.game;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * W klasie Duel przechowuj informację o dwóch uczestnikach (Player) i ich gestach (Gesture).
 * W sumie nie jest podane ile jest tych gestów dlatego nie warto się ograniczać do 2 :)
 * Tutaj skorzystałem z mapy gestures
 */
public class Duel {
    private Player player1;
    private Player player2;
    private Map<Player, Gesture> gestures;
    private Supplier<Result> onEnd;

    public Duel(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.gestures = new HashMap<>();
        
        player1.enterDuel(this);
        player2.enterDuel(this);
    }

    /**
     * Rejestrowanie gestów jeśli któryś z dwóch graczy zrobił ruch
     */
    public void handleGesture(Player player, Gesture gesture) {
        if (player == player1 || player == player2) {
            gestures.put(player, gesture);
            
            if (gestures.size() == 2 && onEnd != null) {
                Result result = onEnd.get();

            }
        }
    }

    public Result evaluate() {
        if (gestures.size() != 2) {
            return null; // Obaj gracze muszą zrobić gest
        }
        
        Gesture gesture1 = gestures.get(player1);
        Gesture gesture2 = gestures.get(player2);
        
        if (gesture1 == null || gesture2 == null) {
            return null; // Obaj gracze muszą zrobić gest
        }
        
        int comparison = gesture1.compareWith(gesture2);
        
        if (comparison == 0) {
            return null; // ?? remis
        } else if (comparison == 1) {
            return new Result(player1, player2); // Player1 wygrywa
        } else {
            return new Result(player2, player1); // Player2 wygrywa
        }
    }

    public void setOnEnd(Supplier<Result> onEnd) {
        this.onEnd = onEnd;
    }

    public void endDuel() {
        if (player1 != null) {
            player1.leaveDuel();
        }
        if (player2 != null) {
            player2.leaveDuel();
        }
    }

    /**
     * W klasie Duel napisz zagnieżdżony rekord Result zawierający dwa pola typu Player: winner i loser.
     * W klasie Duel napisz publiczną, bezargumentową metodę evaluate(), która w przypadku zwycięstwa jednego z graczy zwróci obiekt Result, a w przypadku remisu - null.
     */
    public static record Result(Player winner, Player loser) {}
}
