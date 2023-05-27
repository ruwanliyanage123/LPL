package lk.premier.league.lpl.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Player {
    private String teamName;
    private String playerName;
    private int numberOfRuns;
    private int numberOfWickers;
}
