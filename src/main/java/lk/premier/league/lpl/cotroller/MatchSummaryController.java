package lk.premier.league.lpl.cotroller;

import lk.premier.league.lpl.model.Summary;
import lk.premier.league.lpl.service.api.ScoreService;
import lk.premier.league.lpl.service.impl.ScoreServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("match/")
public class MatchSummaryController {

    @GetMapping(value = "summary")
    public ResponseEntity<Summary> getMatchSummary(){
        ScoreService service = new ScoreServiceImpl();
        String playerWithMostRuns =  service.findPlayerWithMostRuns();
        String playerWithMostWickets =  service.findPlayerWithMostWickets();
        String winners = service.findWinningTeam();
        String matchSummary = service.findMatchSummary();
        Summary summary = new Summary();
        summary.setPlayerWithMostRuns(playerWithMostRuns);
        summary.setPlayerWithMostWickets(playerWithMostWickets);
        summary.setWinners(winners);
        summary.setScoreSummary(matchSummary);
        return new ResponseEntity<>(summary,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
