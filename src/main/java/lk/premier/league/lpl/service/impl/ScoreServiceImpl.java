package lk.premier.league.lpl.service.impl;

import lk.premier.league.lpl.model.Player;
import lk.premier.league.lpl.model.Team;
import lk.premier.league.lpl.service.api.ScoreService;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ScoreServiceImpl implements ScoreService {
    private List<Player> batters = new ArrayList<>();
    private Map<String, Team> teams = new HashMap<>();

    @Override
    public String findPlayerWithMostRuns() {
        String splitBy = ",";
        String line = null;
        try {
            int rowNumber = 0;
            Team team = null;
            BufferedReader br = new BufferedReader(new FileReader("/Users/ruwan/Documents/PROJECT/lpl/src/main/resources/data/match_result.csv"));
            while ((line = br.readLine()) != null) {
                if (rowNumber != 0) {//to ignore the title related row in the .csv file
                    String[] record = line.split(splitBy);
                    String battingTeam = record[2];
                    String striker = record[3];
                    String overs = record[1];
                    int runs = Integer.parseInt(record[6]);
                    int extraRuns = Integer.parseInt(record[7]);
                    //todo: there may be a multiple players with same name, the will be a problem need to fix it
                    if (isNotBatted(batters, striker)) {
                        //need to add the two teams to a map
                        if (!teams.containsKey(battingTeam)) {
                            team = new Team();
                            team.setName(battingTeam);
                            teams.put(battingTeam, team);
                        }
                        Player player = new Player();
                        player.setName(striker);
                        player.setRuns(player.getRuns() + runs);
                        player.setTeam(team);
                        batters.add(player);
                    } else {
                        Player batter = batters.stream().filter(player -> player.getName().equals(striker)).collect(Collectors.toList()).get(0);
                        batter.setRuns(batter.getRuns() + runs);
                    }
                    //To add the extra and runs
                    assert team != null;
                    team.setOvers(overs);
                    team.setTotal(team.getTotal() + runs);
                    team.setExtras(team.getExtras() + extraRuns);
                }
                rowNumber++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        batters.sort(Comparator.comparing(Player::getRuns));
        batters.forEach(h -> System.out.println(h.getTeam().getName() + " : " + h.getName() + " : " + h.getRuns()));
        return batters.get(batters.size() - 1).getName();
    }


    private boolean isNotBatted(List<Player> batters, String name) {
        return batters.stream().noneMatch(batter -> batter.getName().equals(name));
    }

    @Override
    public String findPlayerWithMostWickets() {
        return null;
    }

    @Override
    public String findWinningTeam() {
        List<Team> team = new ArrayList<>(teams.values());
        Team teamA = team.get(0);
        Team teamB = team.get(1);
        int teamASum = teamA.getTotal() + teamA.getExtras();
        int teamBSum = teamB.getTotal() + teamB.getExtras();
        return teamASum > teamBSum ? teamA.getName() + " won by" + getRunGap(teamASum, teamBSum)+" runs" : teamB.getName() + " won by " + getRunGap(teamBSum, teamASum)+" runs";
    }

    private int getRunGap(int sum1, int sum2) {
        return sum1 - sum2;
    }

    @Override
    public String findMatchSummary() {
        List<Team> team = new ArrayList<>(teams.values());
        Team teamA = team.get(0);
        Team teamB = team.get(1);
        int teamASum = teamA.getTotal() + teamA.getExtras();
        int teamBSum = teamB.getTotal() + teamB.getExtras();
        String teamASummary = teamA.getName() + " - " + teamASum + "/" + teamA.getOvers();
        String teamBSummary = teamB.getName() + " - " + teamBSum + "/" + teamB.getOvers();
        return teamASummary + " " + teamBSummary;
    }
}
