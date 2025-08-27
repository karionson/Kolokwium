import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Election {
    private List<Candidate> candidates;
    private ElectionTurn firstTurn;
    private ElectionTurn secondTurn = null;

    public Election(ElectionTurn firstTurn) {
        this.candidates = new ArrayList<>();
        this.firstTurn = firstTurn;
    }

    public ArrayList<Candidate> copyCandidates() {
        ArrayList copy = new ArrayList(candidates);
        return copy;
    }

    public void populateCandidates(String path) {
        String line;
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            while (br.readLine() != null) {
                candidates.add(new Candidate(br.readLine()));
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public void populate() throws FileNotFoundException {
        populateCandidates("path");
        firstTurn.populate("1.csv");
    }

    public ElectionTurn getFirstTurn() {
        return firstTurn;
    }

    public List<Candidate> getCandidates() {
        return candidates;
    }

    public ElectionTurn getSecondTurn() {
        return secondTurn;
    }
}
