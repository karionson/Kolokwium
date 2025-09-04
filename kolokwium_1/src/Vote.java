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

    public static Vote fromCsvLine(String csvLine, List<Candidate> candidates) {
        Vote vote = new Vote();

        String[] columns = csvLine.split(",");
        if (columns.length >= 3) {
            vote.addLocation(columns[2].trim()); // województwo
            vote.addLocation(columns[1].trim()); // powiat
            vote.addLocation(columns[0].trim()); // gmina

            // Pozostałe kolumny to głosy dla kolejnych kandydatów
            for (int i = 3; i < columns.length && i - 3 < candidates.size(); i++) {
                try {
                    Integer votes = Integer.parseInt(columns[i].trim());
                    Candidate candidate = candidates.get(i - 3);
                    vote.addVotesForCandidate(candidate, votes);
                } catch (NumberFormatException e) {
                    System.err.println("Nieprawidłowa liczba głosów: " + columns[i]);
                } catch (IndexOutOfBoundsException e) {
                    System.err.println("Brak kandydata dla kolumny: " + i);
                }
            }
        }

        return vote;
    }

    public static Vote summarize(List<Vote> votes) {
        return summarize(votes, new ArrayList<>());
    }

    public static Vote summarize(List<Vote> votes, List<String> location) {
        Vote summaryVote = new Vote();

        if (votes == null || votes.isEmpty()) {
            summaryVote.setLocation(location);
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

        summaryVote.setLocation(location);

        return summaryVote;
    }

    public static List<Vote> filterByLocation(List<Vote> votes, List<String> locationFilter) {
        List<Vote> filteredVotes = new ArrayList<>();
        
        if (votes == null || locationFilter == null || locationFilter.isEmpty()) {
            return filteredVotes;
        }
        
        for (Vote vote : votes) {
            List<String> voteLocation = vote.getLocation();
            
            if (voteLocation.size() >= locationFilter.size()) {
                boolean matches = true;
                for (int i = 0; i < locationFilter.size(); i++) {
                    if (!voteLocation.get(i).equals(locationFilter.get(i))) {
                        matches = false;
                        break;
                    }
                }
                if (matches) {
                    filteredVotes.add(vote);
                }
            }
        }
        
        return filteredVotes;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        // Obliczamy sumę wszystkich głosów tylko raz (krok 11)
        int totalVotes = votesForCandidate.values().stream()
                .mapToInt(Integer::intValue)
                .sum();
        
        for (Map.Entry<Candidate, Integer> entry : votesForCandidate.entrySet()) {
            Candidate candidate = entry.getKey();
            Integer votes = entry.getValue();
            double percentage = totalVotes > 0 ? Math.round((double) votes / totalVotes * 10000) / 100.0 : 0.0;
            
            sb.append(candidate.name()).append(": ").append(percentage).append("%\n");
        }
        
        return sb.toString();
    }
}
