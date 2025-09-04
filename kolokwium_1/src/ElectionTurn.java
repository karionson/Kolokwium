import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
            boolean firstLine = true;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    if (firstLine) {
                        // Pomijamy pierwszą linię (nagłówki)
                        firstLine = false;
                        continue;
                    }
                    Vote vote = Vote.fromCsvLine(line, this.candidates);
                    this.votes.add(vote);
                }
            }
        }
    }

    public Candidate winner() throws NoWinnerException {
        Vote summary = Vote.summarize(this.votes);
        
        int totalVotes = summary.getVotesForCandidate().values().stream()
                .mapToInt(Integer::intValue)
                .sum();
        
        for (Map.Entry<Candidate, Integer> entry : summary.getVotesForCandidate().entrySet()) {
            Candidate candidate = entry.getKey();
            Integer votes = entry.getValue();
            double percentage = (double) votes / totalVotes * 100;
            
            if (percentage > 50.0) {
                return candidate;
            }
        }
        
        throw new NoWinnerException("Żaden kandydat nie osiągnął ponad 50% głosów");
    }

    public List<Candidate> runoffCandidates() {
        Vote summary = Vote.summarize(this.votes);
        
        return summary.getVotesForCandidate().entrySet().stream()
                .sorted(Map.Entry.<Candidate, Integer>comparingByValue().reversed())
                .limit(2)
                .map(Map.Entry::getKey)
                .toList();
    }

    public Vote summarize() {
        return Vote.summarize(this.votes);
    }

    public Vote summarize(List<String> location) {
        List<Vote> filteredVotes = Vote.filterByLocation(this.votes, location);
        return Vote.summarize(filteredVotes, location);
    }
}