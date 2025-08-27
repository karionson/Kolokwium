import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

class ElectionTurn {
    private List<Candidate> candidates;
    private List<Vote> votes;

    public ElectionTurn(List<Candidate> candidates) {
        this.candidates = new ArrayList<>(candidates);
        this.votes = new ArrayList<>();
    }

    public List<Candidate> getCandidates() {
        return new ArrayList<>(candidates);
    }

    public List<Vote> getVotes() {
        return new ArrayList<>(votes);
    }

    public void setVotes(List<Vote> votes) {
        this.votes = new ArrayList<>(votes);
    }

    public void populate(String filePath) throws IOException {
        this.votes.clear();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    Vote vote = Vote.fromCsvLine(line);
                    this.votes.add(vote);
                }
            }
        }
    }
}