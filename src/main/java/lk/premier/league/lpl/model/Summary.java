package lk.premier.league.lpl.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Summary {
    private String winners;
    private String scoreSummary;
    private String playerWithMostRuns;
    private String playerWithMostWickets;
}
