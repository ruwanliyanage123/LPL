package lk.premier.league.lpl.controller;

import lk.premier.league.lpl.model.Summary;
import lk.premier.league.lpl.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class MatchSummaryController {
    @Autowired
    private ScoreService scoreService;
    @GetMapping(value = "/summary/{path1}/{path2}/{path3}/{path4}")
    public ResponseEntity<Summary> getMatchSummary(@PathVariable String path1, @PathVariable String path2, @PathVariable String path3, @PathVariable String path4) {
        String playerWithMostRuns = scoreService.findPlayerWithMostRuns(path1 + "/" + path2 + "/" + path3 + "/" + path4);
        String playerWithMostWickets = scoreService.findPlayerWithMostWickets();
        String winners = scoreService.findWinningTeam();
        String matchSummary = scoreService.findMatchSummary();

        Summary summary = new Summary();
        summary.setMostRuns(playerWithMostRuns);
        summary.setMostWickets(playerWithMostWickets);
        summary.setWinner(winners);
        summary.setSummary(matchSummary);

        Map<String, String> firstTeamBatting = scoreService.findFirstTeamBatting();
        Map<String, String> secondTeamBatting = scoreService.findSecondTeamBatting();
        Map<String, String> firstTeamBolling = scoreService.findFirstTeamBowling();
        Map<String, String> secondTeamBolling = scoreService.findSecondTeamBowling();
        Map<String, Map<String, String>> score = new HashMap<>();
        score.put("1 Batting", firstTeamBatting);
        score.put("2 Batting", secondTeamBatting);
        score.put("1 Bowling", firstTeamBolling);
        score.put("2 Bowling", secondTeamBolling);
        summary.setScoreCard(score);
        return new ResponseEntity<>(summary, HttpStatus.OK);
    }
}
