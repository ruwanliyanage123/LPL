package lk.premier.league.lpl.cotroller;

import lk.premier.league.lpl.model.Summary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("match/")
public class MatchSummaryController {
    @GetMapping(value = "summary")
    public ResponseEntity<Summary> getMatchSummary(){
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
