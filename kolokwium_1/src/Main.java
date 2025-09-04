import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        try {
            // Krok 1-15: Podstawowa funkcjonalność wyborów
            System.out.println("=== Testowanie systemu wyborczego ===");
            
            Election election = new Election();
            election.populate();
            
            System.out.println("Zwycięzca wyborów: " + election.getWinner().name());
            
            // Krok 10: Test metody toString()
            System.out.println("\n=== Wyniki pierwszej tury (toString) ===");
            Vote firstTurnSummary = election.getFirstTurn().summarize();
            System.out.println(firstTurnSummary);
            
            // Krok 17: Test przeciążonych metod summarize()
            System.out.println("=== Test summarize() bez argumentów ===");
            Vote allVotesSummary = election.getFirstTurn().summarize();
            System.out.println("Łączne wyniki pierwszej tury:");
            System.out.println(allVotesSummary);
            
            System.out.println("\n=== Test summarize() z lokalizacją ===");
            List<String> mazowieckie = Arrays.asList("mazowieckie");
            Vote mazowieckieSummary = election.getFirstTurn().summarize(mazowieckie);
            System.out.println("Wyniki dla województwa mazowieckiego:");
            System.out.println(mazowieckieSummary);
            
            // Krok 18: Test VoivodeshipMap
            System.out.println("\n=== Test VoivodeshipMap ===");
            VoivodeshipMap map = new VoivodeshipMap();
            map.saveToSvg("polska_map.svg");
            System.out.println("Mapa Polski została zapisana do pliku polska_map.svg");
            
            // Krok 19: Generowanie wyników dla każdego województwa
            System.out.println("\n=== Wyniki drugiej tury dla województw ===");
            Map<String, Vote> voivodeshipResults = new HashMap<>();
            List<String> voivodeships = VoivodeshipMap.getVoivodeshipNames();
            
            for (String voivodeship : voivodeships) {
                List<String> location = Arrays.asList(voivodeship);
                Vote summary = election.getSecondTurn().summarize(location);
                voivodeshipResults.put(voivodeship, summary);
                
                // Znajdź zwycięzcę w województwie
                Candidate winner = findWinnerInVote(summary);
                System.out.println(voivodeship + ": " + (winner != null ? winner.name() : "Brak danych"));
            }
            
            // Krok 20: Test SelectableMap
            System.out.println("\n=== Test SelectableMap ===");
            SelectableMap selectableMap = new SelectableMap();
            selectableMap.select("mazowieckie");
            selectableMap.saveToSvg("selectable_map.svg");
            System.out.println("Mapa z zaznaczonym mazowieckim została zapisana do selectable_map.svg");
            
            // Krok 21: Test VoteMap
            System.out.println("\n=== Test VoteMap ===");
            VoteMap voteMap = new VoteMap();
            
            // Znajdź zwycięzców w każdym województwie
            Map<String, Candidate> voivodeshipWinners = new HashMap<>();
            for (Map.Entry<String, Vote> entry : voivodeshipResults.entrySet()) {
                String voivodeship = entry.getKey();
                Vote vote = entry.getValue();
                Candidate winner = findWinnerInVote(vote);
                if (winner != null) {
                    voivodeshipWinners.put(voivodeship, winner);
                }
            }
            
            voteMap.setVoivodeshipResults(voivodeshipWinners);
            
            // Przypisz kolory kandydatom
            Map<Candidate, String> candidateColors = new HashMap<>();
            candidateColors.put(new Candidate("Andrzej Sebastian DUDA"), "blue");
            candidateColors.put(new Candidate("Rafał Kazimierz TRZASKOWSKI"), "red");
            
            voteMap.setCandidateColors(candidateColors);
            voteMap.saveToSvg("vote_map.svg");
            System.out.println("Mapa z wynikami wyborów została zapisana do vote_map.svg");
            
        } catch (IOException e) {
            System.err.println("Błąd wejścia/wyjścia: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Błąd: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static Candidate findWinnerInVote(Vote vote) {
        if (vote.getVotesForCandidate().isEmpty()) {
            return null;
        }
        
        return vote.getVotesForCandidate().entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }
}