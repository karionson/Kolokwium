package org.example.game;

public class Player {
    private Duel duel;

    /**
     * Makes a gesture in the current duel.
     * 
     * @param gesture the gesture to make
     */
    public void makeGesture(Gesture gesture) {
        if (duel != null) {
            duel.handleGesture(this, gesture);
        }
    }

    /**
     * Enters a duel by setting the duel field.
     * 
     * @param duel the duel to enter
     */
    void enterDuel(Duel duel) {
        this.duel = duel;
    }

    /**
     * Leaves the current duel by setting the duel field to null.
     */
    public void leaveDuel() {
        this.duel = null;
    }

    /**
     * Checks if the player is currently in a duel.
     * 
     * @return true if the player is in a duel, false otherwise
     */
    public boolean isDuelling() {
        return this.duel != null;
    }
}
