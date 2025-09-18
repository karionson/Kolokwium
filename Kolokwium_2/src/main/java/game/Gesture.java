package game;

public enum Gesture {
    ROCK, PAPER, SCISSORS;

    public static Gesture fromString(String c) {
        if(c.equals("r")) {
            return ROCK;
        }
        else if(c.equals("p")) {
            return PAPER;
        }
        else if(c.equals("s")) {
            return SCISSORS;
        }
        else return null;
    }
    public int compareWith(Gesture g) {
        if(this.equals(g)) {
            return 0;
        }
        else if(this.equals(ROCK) && g.equals(PAPER)) {
            return -1;
        }
        else if (this.equals(PAPER) && g.equals(SCISSORS)) {
            return -1;
        }
        else if (this.equals(SCISSORS) && g.equals(ROCK)) {
            return -1;
        }
        else if (this.equals(PAPER) && g.equals(ROCK)) {
            return 1;
        }
        else return 1;
    }
}
