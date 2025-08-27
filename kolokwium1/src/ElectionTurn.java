import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ElectionTurn {
    private List<Candidate> candidates;
    private List<Vote> votes;

    public ElectionTurn(List<Candidate> candidates, List<Vote> votes) {
        this.candidates = new ArrayList<>();
        this.votes = new ArrayList<>();
    }

    public void populate(String path) throws FileNotFoundException {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            this.votes.add(Vote.fromCSVLine(br.readLine()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

