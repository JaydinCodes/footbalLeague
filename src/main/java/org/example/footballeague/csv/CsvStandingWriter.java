package org.example.footballeague.csv;
import org.example.footballeague.domain.TeamStanding;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class CsvStandingWriter {

    public void write(List<TeamStanding> standings, Path file) throws IOException{
        try (BufferedWriter writer = Files.newBufferedWriter(file)){
            writer.write(
                    "teamName,played,won,draw,lost,goalsFor,goalsAgainst,goalDifference,points"
            );

            writer.newLine();

            for (TeamStanding standing : standings) {
                writer.write(
                        standing.teamName + "," +
                                standing.played + "," +
                                standing.won + "," +
                                standing.draw + "," +
                                standing.lost + "," +
                                standing.goalsFor + "," +
                                standing.goalsAgainst + "," +
                                standing.getGoalDifference() + "," +
                                standing.points
                );

                writer.newLine();
            }
        }
    }
}
