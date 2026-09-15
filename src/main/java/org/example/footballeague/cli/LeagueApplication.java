package org.example.footballeague.cli;

import org.example.footballeague.csv.CsvMatchReader;
import org.example.footballeague.csv.CsvStandingWriter;
import org.example.footballeague.domain.MatchResult;
import org.example.footballeague.domain.TeamStanding;
import org.example.footballeague.service.LeagueTableCalculator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class LeagueApplication {

    public static void main(String[] args) {

        if (args.length != 2) {
            printUsage();
            System.exit(1);
        }

        Path inputFile = Paths.get(args[0]);
        Path outputFile = Paths.get(args[1]);

        try {
            run(inputFile, outputFile);

            System.out.println(
                    "League standings successfully written to: "
                            + outputFile
            );

        } catch (IOException | IllegalArgumentException e) {

            System.err.println(
                    "Error: " + e.getMessage()
            );

            System.exit(1);
        }
    }

    private static void run(
            Path inputFile,
            Path outputFile) throws IOException {

        CsvMatchReader reader = new CsvMatchReader();
        CsvStandingWriter writer = new CsvStandingWriter();
        LeagueTableCalculator calculator =
                new LeagueTableCalculator();

        List<MatchResult> matches =
                reader.read(inputFile);

        List<TeamStanding> standings =
                calculator.calculate(matches);

        createParentDirectory(outputFile);

        writer.write(
                standings,
                outputFile
        );
    }

    private static void createParentDirectory(
            Path outputFile) throws IOException {

        Path parentDirectory =
                outputFile.getParent();

        if (parentDirectory != null) {
            Files.createDirectories(parentDirectory);
        }
    }

    private static void printUsage() {

        System.err.println(
                "Usage: java -jar footbalLeague.jar "
                        + "<input-file> <output-file>"
        );
    }
}