import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

class Election {
    private List<Candidate> candidates;
    private ElectionTurn firstTurn;
    private ElectionTurn secondTurn;

    public Election() {
        this.candidates = new ArrayList<>();
    }

    public Election(List<Candidate> candidates) {
        this.candidates = new ArrayList<>(candidates);
    }

    public List<Candidate> getCandidates() {
        return new ArrayList<>(candidates);
    }

    public void populateCandidates(String filePath) throws IOException {
        this.candidates.clear();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    this.candidates.add(new Candidate(line));
                }
            }
        }
    }

    public void populate() throws IOException {
        populateCandidates("candidates.txt");

        this.firstTurn = new ElectionTurn(this.candidates);
        firstTurn.populate("1.csv");
    }

    public ElectionTurn getFirstTurn() {
        return firstTurn;
    }

    public void setFirstTurn(ElectionTurn firstTurn) {
        this.firstTurn = firstTurn;
    }

    public ElectionTurn getSecondTurn() {
        return secondTurn;
    }

    public void setSecondTurn(ElectionTurn secondTurn) {
        this.secondTurn = secondTurn;
    }
}