package game;


import java.util.function.Function;

public class Duel {
    private Player player1;
    private Gesture gesture1;
    private Player player2;
    private Gesture gesture2;
    private Function<Void, Result> onEnd;

    public Duel(Player player1, Player player2) {
        if(player1 != player2) {
            this.player1 = player1;
            this.player2 = player2;
            player1.enterDuel(this);
            player2.enterDuel(this);
        }
        else {
            throw new IllegalArgumentException("Gracz nie moze grac z samym soba");
        }
    }

    public void handleGesture(Player player, Gesture gesture) {
        player.makeGesture(gesture);
    }

    public record Result(Player winner, Player loser) {

    }

    public void mutate(Function<Void, Result> onEnd) {
        this.onEnd = onEnd;
    }

    public Result evaluate() {
        if(gesture1.equals(gesture2)) {
            return null;
        }
        else if(gesture1.compareWith(gesture2) < 0) {
            return new Result(player2, player1);
        }
        else return new  Result(player1, player2);

    }

    public Function<Void, Result> getOnEnd() {
        return onEnd;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Gesture getGesture1() {
        return gesture1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Gesture getGesture2() {
        return gesture2;
    }
}
