import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static javax.swing.UIManager.get;

public class Vote {
    private Map<Candidate, Integer> votesForCandidates;
    private List<String> location;

    public Vote(Map<Candidate, Integer> votesForCandidates, ArrayList<String> location) {
        this.votesForCandidates = votesForCandidates;
        this.location = location;
    }

    public static Vote fromCSVLine(String line) throws FileNotFoundException {
        ArrayList<String> location = new ArrayList<>();
        Map<Candidate, Integer> votesForCandidates = new HashMap<>();

        String[] lineArray = line.split(",");

        location.add(lineArray[0]);
        location.add(lineArray[1]);
        location.add(lineArray[2]);

        for (int i = 3; i < lineArray.length; i++) {
            votesForCandidates.put(new Candidate(lineArray[i]), Integer.parseInt(lineArray[i]));
        }
        return new Vote(votesForCandidates, location);

    }

    public Vote summarize(List<Vote> votes) {
        Map<Candidate, Integer> result = new HashMap<>();
        Vote firstElement = votes.getFirst();
        Set<Candidate> candidates = firstElement.getVotesForCandidates().keySet();
        candidates.forEach(candidate -> result.put(candidate, 0));
        // biedron 0
        // duda 0

        for(Vote vote : votes){
            Map<Candidate, Integer> votesForCandidates = vote.getVotesForCandidates();
            for (Candidate candidate : candidates){
                result.put(candidate, result.get(candidate) + votesForCandidates.getOrDefault(candidate, 0));
            }
        }
        return new Vote(result, null);
    }

    public int votes (Candidate candidate) {
        return votesForCandidates.getOrDefault(candidate, 0);
    }

    public float percentage(Candidate candidate) {
        int candidateVotes = votesForCandidates.get(candidate);
        Set<Candidate> candidates = this.getVotesForCandidates().keySet();
        int sum = 0;
        for(Candidate c : candidates){
            sum += votesForCandidates.get(c);
        }
        return votesForCandidates.get(candidate) / sum * 100;
    }

    public Map<Candidate, Integer> getVotesForCandidates() {
        return votesForCandidates;
    }

    public List<String> getLocation() {
        return location;
    }

    @Override
    public String toString() {
        Set<Candidate> candidates = this.getVotesForCandidates().keySet();
        StringBuilder sb = new StringBuilder();
        for (Candidate candidate : candidates) {
            sb.append(candidate.toString()).append(" " + percentage(candidate) + "\n");
        }
        return sb.toString();
    }
}