import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class Election {
    private List<Candidate> candidates;
    private ElectionTurn firstTurn;
    private ElectionTurn secondTurn;
    private Candidate winner;

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
        populateCandidates("kandydaci.txt");

        this.firstTurn = new ElectionTurn(this.candidates);
        firstTurn.populate("1.csv");
        
        try {
            this.winner = firstTurn.winner();
        } catch (NoWinnerException e) {
            // Jeśli nie ma zwycięzcy w pierwszej turze, przeprowadź drugą turę
            List<Candidate> runoffCandidates = firstTurn.runoffCandidates();
            this.secondTurn = new ElectionTurn(runoffCandidates);
            secondTurn.populate("2.csv");
            
            try {
                this.winner = secondTurn.winner();
            } catch (NoWinnerException e2) {
                // Jeśli również w drugiej turze nie ma zwycięzcy, 
                // wybierz kandydata z największą liczbą głosów
                Vote secondTurnSummary = secondTurn.summarize();
                this.winner = secondTurnSummary.getVotesForCandidate().entrySet().stream()
                        .max(Map.Entry.comparingByValue())
                        .map(Map.Entry::getKey)
                        .orElse(null);
                
                if (this.winner == null) {
                    throw new RuntimeException("Nie można określić zwycięzcy wyborów");
                }
            }
        }
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

    public Candidate getWinner() {
        return winner;
    }

    public void setWinner(Candidate winner) {
        this.winner = winner;
    }
}