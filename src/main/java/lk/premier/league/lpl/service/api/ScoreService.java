package lk.premier.league.lpl.service.api;

import org.springframework.stereotype.Service;

/**
 * Service class to maintain the business logics to match summary
 */

public interface ScoreService {

    String findPlayerWithMostRuns();
    String findPlayerWithMostWickets();
    String findWinningTeam();

    String findMatchSummary();
}
