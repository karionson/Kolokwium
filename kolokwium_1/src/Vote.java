import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class Vote {
    private Map<Candidate, Integer> votesForCandidate;
    private List<String> location;

    public Vote() {
        this.votesForCandidate = new HashMap<>();
        this.location = new ArrayList<>();
    }

    public Map<Candidate, Integer> getVotesForCandidate() {
        return new HashMap<>(votesForCandidate);
    }

    public void setVotesForCandidate(Map<Candidate, Integer> votesForCandidate) {
        this.votesForCandidate = new HashMap<>(votesForCandidate);
    }

    public List<String> getLocation() {
        return new ArrayList<>(location);
    }

    public void setLocation(List<String> location) {
        this.location = new ArrayList<>(location);
    }

    public void addVotesForCandidate(Candidate candidate, Integer votes) {
        votesForCandidate.put(candidate, votes);
    }

    public void addLocation(String locationItem) {
        location.add(locationItem);
    }

    public static Vote fromCsvLine(String csvLine) {
        Vote vote = new Vote();

        String[] columns = csvLine.split(",");
        if (columns.length >= 3) {
            for (int i = 0; i < 3 && i < columns.length; i++) {
                vote.addLocation(columns[i].trim());
            }

            for (int i = 3; i < columns.length; i += 2) {
                if (i + 1 < columns.length) {
                    String candidateName = columns[i].trim();
                    try {
                        Integer votes = Integer.parseInt(columns[i + 1].trim());
                        Candidate candidate = new Candidate(candidateName);
                        vote.addVotesForCandidate(candidate, votes);
                    } catch (NumberFormatException e) {
                        System.err.println("Nieprawidłowa liczba głosów: " + columns[i + 1]);
                    }
                }
            }
        }

        return vote;
    }

    public static Vote summarize(List<Vote> votes) {
        Vote summaryVote = new Vote();

        if (votes == null || votes.isEmpty()) {
            return summaryVote;
        }

        for (Vote vote : votes) {
            Map<Candidate, Integer> voteMap = vote.getVotesForCandidate();
            for (Map.Entry<Candidate, Integer> entry : voteMap.entrySet()) {
                Candidate candidate = entry.getKey();
                Integer votesCount = entry.getValue();

                Integer currentSum = summaryVote.votesForCandidate.get(candidate);
                if (currentSum == null) {
                    currentSum = 0;
                }
                summaryVote.votesForCandidate.put(candidate, currentSum + votesCount);
            }
        }

        summaryVote.location.clear();

        return summaryVote;
    }

    public Integer votes(Candidate candidate) {
        return votesForCandidate.getOrDefault(candidate, 0);
    }

    public Double percentage(Candidate candidate) {
        Integer candidateVotes = votesForCandidate.getOrDefault(candidate, 0);

        int totalVotes = votesForCandidate.values().stream()
                .mapToInt(Integer::intValue)
                .sum();

        if (totalVotes == 0) {
            return 0.0;
        }

        return Math.round((double) candidateVotes / totalVotes * 10000) / 100.0;
    }
}
