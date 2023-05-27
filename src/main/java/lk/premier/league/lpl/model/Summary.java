package lk.premier.league.lpl.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Summary {
    private String winner;
    private String mostRuns;
    private String mostWickets;
    private Map<String, Map<String, String>> scoreCard;
    private String summary;
}
