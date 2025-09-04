import java.util.*;

public class VoteMap extends VoivodeshipMap {
    private Map<String, Candidate> voivodeshipWinners;
    private Map<Candidate, String> candidateColors;
    
    public VoteMap() {
        this.voivodeshipWinners = new HashMap<>();
        this.candidateColors = new HashMap<>();
    }
    
    public void setVoivodeshipResults(Map<String, Candidate> results) {
        this.voivodeshipWinners = new HashMap<>(results);
    }
    
    public void setCandidateColors(Map<Candidate, String> colors) {
        this.candidateColors = new HashMap<>(colors);
    }
    
    @Override
    protected String getVoivodeshipColor(String voivodeship) {
        Candidate winner = voivodeshipWinners.get(voivodeship);
        if (winner != null) {
            return candidateColors.getOrDefault(winner, "#808080"); // szary
        }
        return "#808080"; // szary
    }
}
