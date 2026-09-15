package org.example.footballeague.cli;

import org.example.footballeague.csv.CsvMatchReader;
import org.example.footballeague.csv.CsvStandingWriter;
import org.example.footballeague.domain.MatchResult;
import org.example.footballeague.domain.TeamStanding;
import org.example.footballeague.service.LeagueTableCalculator;

import java.nio.file.Path;
import java.util.List;

public class LeagueApplication {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java -jar football-league.jar <input-file> <output-file>");
            System.exit(1);
        }

        Path inputFile = Path.of(args[0]);
        Path outputFile = Path.of(args[1]);

        try{
            CsvMatchReader reader = new CsvMatchReader();
            CsvStandingWriter writer = new CsvStandingWriter();

            LeagueTableCalculator calculator = new LeagueTableCalculator();

            List<MatchResult> matches = reader.read(inputFile);
            List<TeamStanding> standings = calculator.calculateLeagueTable(matches);

            writer.write(standings, outputFile);

            System.out.println(
                    "League standings successfully written to: "
                            + outputFile
            );
        } catch (Exception e){
            System.err.println(
                    "Error: " + e.getMessage()
            );

            System.exit(1);
        }
    }

}
