package org.example.footballeague.csv;

import org.example.footballeague.domain.MatchResult;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CsvMatchReaderTest {

    @Test
    void readsValidCsv() throws Exception {

        Path file = Files.createTempFile("matches", ".csv");

        Files.write(
                file,
                (
                        "match_id,season,competition,matchweek,date,home_team,away_team,home_goals,away_goals,result\n" +
                                "1,1974/75,English First Division,1,1974-08-17,Liverpool,Arsenal,2,1,H\n"
                ).getBytes()
        );

        CsvMatchReader reader = new CsvMatchReader();

        List<MatchResult> matches = reader.read(file);

        assertEquals(1, matches.size());

        MatchResult match = matches.get(0);

        assertEquals(1, match.getMatchWeek());
        assertEquals("Liverpool", match.getHomeTeam());
        assertEquals("Arsenal", match.getAwayTeam());
        assertEquals(2, match.getHomeGoals());
        assertEquals(1, match.getAwayGoals());

        Files.deleteIfExists(file);
    }

    @Test
    void rejectsInvalidColumnCount() throws Exception {

        Path file = Files.createTempFile("matches", ".csv");

        Files.write(
                file,
                (
                        "match_id,season,competition,matchweek,date,home_team,away_team,home_goals,away_goals,result\n" +
                                "1,1974/75,English First Division,1,1974-08-17,Liverpool,Arsenal,2\n"
                ).getBytes()
        );

        CsvMatchReader reader = new CsvMatchReader();

        assertThrows(
                IllegalArgumentException.class,
                () -> reader.read(file)
        );

        Files.deleteIfExists(file);
    }

    @Test
    void rejectsInvalidScore() throws Exception {

        Path file = Files.createTempFile("matches", ".csv");

        Files.write(
                file,
                (
                        "match_id,season,competition,matchweek,date,home_team,away_team,home_goals,away_goals,result\n" +
                                "1,1974/75,English First Division,1,1974-08-17,Liverpool,Arsenal,abc,1,H\n"
                ).getBytes()
        );

        CsvMatchReader reader = new CsvMatchReader();

        assertThrows(
                IllegalArgumentException.class,
                () -> reader.read(file)
        );

        Files.deleteIfExists(file);
    }

    @Test
    void rejectsMissingInputFile() {

        CsvMatchReader reader = new CsvMatchReader();

        Path file = Path.of("does-not-exist.csv");

        assertThrows(
                java.io.IOException.class,
                () -> reader.read(file)
        );
    }
}