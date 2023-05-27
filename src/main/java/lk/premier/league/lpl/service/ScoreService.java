package lk.premier.league.lpl.service;


import java.util.Map;

/**
 * Service class to maintain the business logics to match summary
 */

public interface ScoreService {
    /**
     * find player with most runs
     *
     * @param path csv file path
     * @return player name
     */
    String findPlayerWithMostRuns(String path);

    /**
     * find player with most wickets
     *
     * @return player name
     */
    String findPlayerWithMostWickets();

    /**
     * winner name
     *
     * @return winners
     */
    String findWinningTeam();

    /**
     * summary
     *
     * @return summary
     */
    String findMatchSummary();

    /**
     * fist batting team score card
     *
     * @return score card
     */
    Map<String, String> findFirstTeamBatting();

    /**
     * second batting team score card
     *
     * @return score card
     */
    Map<String, String> findSecondTeamBatting();

    /**
     * fist bowling team score card
     *
     * @return score card
     */
    Map<String, String> findSecondTeamBowling();

    /**
     * second bowling team score card
     *
     * @return score card
     */
    Map<String, String> findFirstTeamBowling();
}
