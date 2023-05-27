package lk.premier.league.lpl.service.api;

import java.util.Map;

/**
 * Service class to maintain the business logics to match summary
 */
public interface ScoreService {

    String findPlayerWithMostRuns();
    String findPlayerWithMostWickets();
    String findWinningTeam();
}
