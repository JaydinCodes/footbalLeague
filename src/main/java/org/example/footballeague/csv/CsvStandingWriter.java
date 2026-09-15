package org.example.footballeague.csv;

import org.example.footballeague.domain.TeamStanding;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;

public class CsvStandingWriter {

    public void write(
            List<TeamStanding> standings,
            Path file) throws IOException {

        try (BufferedWriter writer =
                     Files.newBufferedWriter(file)) {

            writer.write(
                    "Pos,Team,P,W,D,L,F,A,GAvg,Pts"
            );

            writer.newLine();

            int position = 1;

            for (TeamStanding standing : standings) {

                writer.write(
                        position + "," +
                                standing.teamName + "," +
                                standing.played + "," +
                                standing.won + "," +
                                standing.draw + "," +
                                standing.lost + "," +
                                standing.goalsFor + "," +
                                standing.goalsAgainst + "," +
                                String.format(
                                        Locale.US,
                                        "%.3f",
                                        standing.getGoalAverage()
                                ) + "," +
                                standing.points
                );

                writer.newLine();

                position++;
            }
        }
    }
}