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
    private List<Player> bowlers = new ArrayList<>();
    private Map<String, Team> teams = new HashMap<>();

    public ScoreServiceImpl() {
    }

    @Override
    public String findPlayerWithMostRuns() {
        createScoreCardForBatters();
        batters.sort(Comparator.comparing(Player::getRuns));
        //batters.forEach(h -> System.out.println(h.getTeam().getName() + " : " + h.getName() + " : " + h.getRuns()));
        return batters.get(batters.size() - 1).getName();
    }

    @Override
    public String findPlayerWithMostWickets() {
        bowlers.sort(Comparator.comparing(Player::getWickets));
        bowlers.forEach(h -> System.out.println(h.getTeam().getName() + " : " + h.getName() + " : " + h.getWickets()));
        return bowlers.get(bowlers.size() - 1).getName();
    }

    @Override
    public String findWinningTeam() {
        List<Team> team = new ArrayList<>(teams.values());
        Team teamA = team.get(0);
        Team teamB = team.get(1);
        int teamASum = teamA.getTotal() + teamA.getExtras();
        int teamBSum = teamB.getTotal() + teamB.getExtras();
        return teamASum > teamBSum ? teamA.getName() + " won by " + getRunGap(teamASum, teamBSum) + " runs" : teamB.getName() + " won by " + getRunGap(teamBSum, teamASum) + " runs";
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

    private void createScoreCardForBatters() {
        String splitBy = ",";
        String line = null;
        try {
            int rowNumber = 0;
            Team teamBat = null;
            Team teamBowl = null;
            BufferedReader br = new BufferedReader(new FileReader("/Users/ruwan/Documents/PROJECT/lpl/src/main/resources/data/match_result.csv"));
            while ((line = br.readLine()) != null) {
                if (rowNumber != 0) {//to ignore the title related row in the .csv file
                    String[] record = line.split(splitBy);
                    String battingTeam = record[2];
                    String striker = record[3];
                    String overs = record[1];
                    int runs = Integer.parseInt(record[6]);
                    int extraRuns = Integer.parseInt(record[7]);

                    //bowling
                    String bowler = record[5];
                    String inning = record[0];
                    String wicket = null;
                    if (13 <= record.length) {
                        wicket = record[12];
                    }
                    //todo: there may be a multiple players with same name, the will be a problem need to fix it
                    if (isNotBatted(batters, striker)) {
                        //need to add the two teams to a map
                        if (!teams.containsKey(inning)) {
                            if (!teams.isEmpty()) {
                                setFirstBowlingTeam(battingTeam);
                            }
                            teamBat = new Team();
                            teamBat.setName(battingTeam);
                            teams.put(inning, teamBat);
                        }
                        Player player = new Player();
                        player.setName(striker);
                        player.setRuns(player.getRuns() + runs);
                        player.setTeam(teamBat);
                        batters.add(player);
                    } else {
                        Player batter = batters.stream().filter(player -> player.getName().equals(striker)).collect(Collectors.toList()).get(0);
                        batter.setRuns(batter.getRuns() + runs);
                    }

                    //bowling
                    if (isNotBowled(bowlers, bowler)) {
                        teamBowl = new Team();
                        if (inning.equals(String.valueOf(1))) {
                            teamBowl.setName(String.valueOf(1));
                        } else {
                            teamBowl.setName(teams.get(String.valueOf(1)).getName());
                        }
                        Player player = new Player();
                        player.setTeam(teamBowl);
                        player.setName(bowler);
                        bowlers.add(player);
                    }

                    if (wicket != null && !wicket.isEmpty() && !wicket.equalsIgnoreCase("run out")) {
                        Player bowl = bowlers.stream().filter(player -> player.getName().equals(bowler)).collect(Collectors.toList()).get(0);
                        bowl.setWickets(bowl.getWickets() + 1);
                    }
                    //To add the extra and runs
                    assert teamBat != null;
                    teamBat.setOvers(overs);
                    teamBat.setTotal(teamBat.getTotal() + runs);
                    teamBat.setExtras(teamBat.getExtras() + extraRuns);
                }
                rowNumber++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setFirstBowlingTeam(String teamName) {
        List<Player> filtered = bowlers.stream().filter(player -> player.getTeam().getName().equals("1")).collect(Collectors.toList());
        filtered.stream().map(player -> {
            Team team = player.getTeam();
            team.setName(teamName);
            return player;
        }).collect(Collectors.toList());
    }

    private boolean isNotBatted(List<Player> batter, String name) {
        return batter.stream().noneMatch(bat -> bat.getName().equals(name));
    }

    private boolean isNotBowled(List<Player> bowler, String name) {
        return bowler.stream().noneMatch(bow -> bow.getName().equals(name));
    }

    private int getRunGap(int sum1, int sum2) {
        return sum1 - sum2;
    }
}
