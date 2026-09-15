package org.example.footballeague.cli;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class LeagueApplicationTest {

    @Test
    void shouldReadMatchesCalculateStandingsAndWriteOutput()
            throws Exception {

        Path inputFile =
                Files.createTempFile(
                        "matches",
                        ".csv"
                );

        Path outputDirectory =
                Files.createTempDirectory(
                        "league-output"
                ).resolve("nested");

        Path outputFile =
                outputDirectory.resolve(
                        "standings.csv"
                );

        String csv =
                "match_id,season,competition,matchweek,date," +
                        "home_team,away_team,home_goals,away_goals,result\n" +
                        "1,1974/75,First Division,1,1974-08-17," +
                        "Liverpool,Everton,2,0,H\n" +
                        "2,1974/75,First Division,1,1974-08-17," +
                        "Arsenal,Chelsea,1,1,D\n" +
                        "3,1974/75,First Division,2,1974-08-24," +
                        "Everton,Arsenal,0,2,A\n";

        Files.writeString(
                inputFile,
                csv
        );

        LeagueApplication.main(
                new String[]{
                        inputFile.toString(),
                        outputFile.toString()
                }
        );

        assertTrue(
                Files.exists(outputFile)
        );

        String output =
                Files.readString(outputFile);

        assertTrue(
                output.contains("Team")
        );

        assertTrue(
                output.contains("Liverpool")
        );

        assertTrue(
                output.contains("Arsenal")
        );

        assertTrue(
                output.contains("Everton")
        );

        assertTrue(
                output.contains("Chelsea")
        );

        Files.deleteIfExists(inputFile);
        Files.deleteIfExists(outputFile);
        Files.deleteIfExists(outputDirectory);
    }
}