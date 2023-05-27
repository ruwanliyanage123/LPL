package lk.premier.league.lpl.service.impl;

import lk.premier.league.lpl.model.Player;
import lk.premier.league.lpl.service.api.ScoreService;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ScoreServiceImpl implements ScoreService {
    private List<Player> batters = new ArrayList<>();
    private Set<String> teamNames = new HashSet<>();
    private int extraRuns = 0;
    @Override
    public String findPlayerWithMostRuns() {
        String splitBy = ",";
        String line = null;
        try
        {
            int rowNumber = 0;
            BufferedReader br = new BufferedReader(new FileReader("/Users/ruwan/Documents/PROJECT/lpl/src/main/resources/data/match_result.csv"));
            while ((line = br.readLine()) != null)
            {
                if(rowNumber != 0) {
                    String[] record = line.split(splitBy);
                    String battingTeam = record[2];
                    String striker = record[3];
                    teamNames.add(battingTeam);
                    int runs = Integer.parseInt(record[6]);
                    if (batters.stream().noneMatch(player -> player.getTeamName().equals(battingTeam) && player.getPlayerName().equals(striker))) {
                        Player player = new Player();
                        player.setPlayerName(striker);
                        player.setTeamName(battingTeam);
                        batters.add(player);
                    } else {
                        Player batter = batters.stream().filter(player -> player.getPlayerName().equals(striker)).collect(Collectors.toList()).get(0);
                        batter.setNumberOfRuns(batter.getNumberOfRuns() + runs);
                    }
                }
                rowNumber++;
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        //-----------
        batters.sort(Comparator.comparing(Player::getNumberOfRuns));

        batters.forEach(h-> System.out.println(h.getPlayerName() +" : "+ h.getNumberOfRuns()));
        return batters.get(batters.size()-1).getPlayerName();
    }

    @Override
    public String findPlayerWithMostWickets() {
        List<Player> players = new ArrayList<>();
        String splitBy = ",";
        String line = null;
        try
        {
            int rowNumber = 0;
            BufferedReader br = new BufferedReader(new FileReader("/Users/ruwan/Documents/PROJECT/lpl/src/main/resources/data/match_result.csv"));
            while ((line = br.readLine()) != null)
            {
                if(rowNumber != 0) {
                    String[] record = line.split(splitBy);
                    String battingTeam = record[2];
                    String striker = record[3];
                    int runs = Integer.parseInt(record[6]);
                    extraRuns += Integer.parseInt(record[7]);
                    if (players.stream().noneMatch(player -> player.getTeamName().equals(battingTeam) && player.getPlayerName().equals(striker))) {
                        Player player = new Player();
                        player.setPlayerName(striker);
                        player.setTeamName(battingTeam);
                        players.add(player);
                    } else {
                        Player batter = players.stream().filter(player -> player.getPlayerName().equals(striker)).collect(Collectors.toList()).get(0);
                        batter.setNumberOfRuns(batter.getNumberOfRuns() + runs);
                    }
                }
                rowNumber++;
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        players.sort(Comparator.comparing(Player::getNumberOfRuns));

        players.forEach(h-> System.out.println(h.getPlayerName() +" : "+ h.getNumberOfRuns()));
        return players.get(players.size()-1).getPlayerName();
    }

    @Override
    public String findWinningTeam() {
        String teamA = new ArrayList<>(teamNames).get(0);
        String teamB = new ArrayList<>(teamNames).get(1);
        int teamATotal = batters.stream().filter(team->team.getTeamName().equals(teamA)).mapToInt(Player::getNumberOfRuns).sum();
        int teamBTotal = batters.stream().filter(team->team.getTeamName().equals(teamB)).mapToInt(Player::getNumberOfRuns).sum();
        return teamATotal > teamBTotal? teamA : teamB;
    }

    @Override
    public String findMatchSummary() {
        String teamA = new ArrayList<>(teamNames).get(0);
        String teamB = new ArrayList<>(teamNames).get(1);
        int teamATotal = batters.stream().filter(team->team.getTeamName().equals(teamA)).mapToInt(Player::getNumberOfRuns).sum();
        int teamBTotal = batters.stream().filter(team->team.getTeamName().equals(teamB)).mapToInt(Player::getNumberOfRuns).sum();
        return teamA + "-" + teamATotal + " " + teamB + "-" + teamBTotal;
    }
}
