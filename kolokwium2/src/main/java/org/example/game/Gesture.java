package org.example.game;

public enum Gesture {
    ROCK,
    PAPER,
    SCISSORS;

    public static Gesture fromString(String str) {
        if (str == null) {
            throw new IllegalArgumentException("Input nie może być null");
        }

        return switch (str.toLowerCase()) {
            case "r" -> ROCK;    // Kamień
            case "p" -> PAPER;   // Papier
            case "s" -> SCISSORS; // Nożyce
            default -> throw new IllegalArgumentException("Nieprawidłowy string gestu: " + str +
                    ". Użyj 'r' dla KAMIEŃ, 'p' dla PAPIER, 's' dla NOŻYCE");
        };
    }


    public int compareWith(Gesture other) {
        // Remis - oba gesty są takie same
        if (this == other) {
            return 0;
        }

        switch (this) {
            case ROCK:
                return (other == SCISSORS) ? 1 : -1; // Kamień wygrywa z nożycami, przegrywa z papierem
            case PAPER:
                return (other == ROCK) ? 1 : -1; // Papier wygrywa z kamieniem, przegrywa z nożycami
            case SCISSORS:
                return (other == PAPER) ? 1 : -1;  // Nożyce wygrywają z papierem, przegrywają z kamieniem
            default:
                throw new IllegalStateException("Nieznany gest: " + this);
        }
    }
}
