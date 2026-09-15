package org.example.footballeague.csv;

import org.example.footballeague.domain.TeamStanding;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CsvStandingWriterTest {

    @Test
    void writesStandingToCsv() throws Exception {

        TeamStanding standing =
                new TeamStanding("Liverpool");

        standing.recordMatch(3, 1);
        standing.recordMatch(3, 1);
        standing.recordMatch(2, 2);
        standing.recordMatch(0, 2);
        standing.recordMatch(4, 4);
        standing.recordMatch(2, 0);
        standing.recordMatch(2, 0);
        standing.recordMatch(1, 1);
        standing.recordMatch(1, 0);
        standing.recordMatch(0, 0);

        List<TeamStanding> standings =
                Collections.singletonList(standing);

        Path file =
                Files.createTempFile("standings", ".csv");

        CsvStandingWriter writer =
                new CsvStandingWriter();

        writer.write(standings, file);

        List<String> lines =
                Files.readAllLines(file);

        assertEquals(2, lines.size());

        assertEquals(
                "Pos,Team,P,W,D,L,F,A,GAvg,Pts",
                lines.get(0)
        );

        assertEquals(
                "1,Liverpool,10,5,4,1,18,11,1.636,14",
                lines.get(1)
        );

        Files.deleteIfExists(file);
    }
}