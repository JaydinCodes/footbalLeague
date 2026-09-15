package org.example.footballeague.csv;
import org.example.footballeague.domain.MatchResult;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CsvMatchReaderTest {

    @Test
    void readMatchesFromCsv() throws Exception{
        Path file = Files.createTempFile("matches", ".csv");

        Files.write(file, (
                "match_id,season,competition,matchweek,date,home_team,away_team,home_goals,away_goals,result\n" +
                        "1,1974/75,English First Division,1,1974-08-17,Liverpool,Arsenal,2,1,H\n"
        ).getBytes());

        CsvMatchReader reader = new CsvMatchReader();

        List<MatchResult> matches = reader.read(file);

        assertEquals(1, matches.size());

        MatchResult match = matches.get(0);

        assertEquals(1, match.matchWeek);
        assertEquals("Liverpool", match.homeTeam);
        assertEquals("Arsenal", match.awayTeam);
        assertEquals(2, match.homeGoals);
        assertEquals(1, match.awayGoals);

        Files.delete(file);
    }

}
