package org.example.footballeague.csv;

import org.example.footballeague.domain.TeamStanding;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CsvStandingWriterTest {

    @Test
    void writesStandingToCsv() throws Exception {
        TeamStanding standing = new TeamStanding();
        standing.teamName = "Liverpool";
        standing.played = 10;
        standing.won = 6;
        standing.draw = 2;
        standing.lost = 2;
        standing.goalsFor = 18;
        standing.goalsAgainst = 10;
        standing.points = 14;

        List<TeamStanding> standings = new ArrayList<>();
        standings.add(standing); // <-- was missing

        Path file = Files.createTempFile("test", ".csv");
        CsvStandingWriter writer = new CsvStandingWriter();

        writer.write(standings, file);

        List<String> lines = Files.readAllLines(file);

        assertEquals(
                "Pos,Team,P,W,D,L,F,A,GAvg,Pts",
                lines.get(0)
        );

        assertEquals(
                "1,Liverpool,10,6,2,2,18,10,1.800,14",
                lines.get(1)
        );

        Files.deleteIfExists(file);
    }

}
