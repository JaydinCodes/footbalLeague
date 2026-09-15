package org.example.footballeague.csv;

import org.example.footballeague.domain.MatchResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CsvMatchReader {

    private static final String HEADER =
            "match_id,season,competition,matchweek,date,home_team,away_team,home_goals,away_goals,result";

    public List<MatchResult> read(Path file) throws IOException {

        if (file == null) {
            throw new IllegalArgumentException(
                    "Input file cannot be null"
            );
        }

        if (!Files.exists(file)) {
            throw new IOException(
                    "Input file does not exist: " + file
            );
        }

        if (!Files.isRegularFile(file)) {
            throw new IOException(
                    "Input path is not a file: " + file
            );
        }

        List<MatchResult> matches = new ArrayList<>();

        try (BufferedReader reader =
                     Files.newBufferedReader(file)) {

            String line;
            boolean firstLine = true;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {

                lineNumber++;

                if (line.trim().isEmpty()) {
                    continue;
                }

                if (firstLine) {

                    firstLine = false;

                    if (line.equalsIgnoreCase(HEADER)) {
                        continue;
                    }

                    throw new IllegalArgumentException(
                            "Invalid CSV header on line "
                                    + lineNumber
                    );
                }

                String[] columns = line.split(",", -1);

                if (columns.length != 10) {
                    throw new IllegalArgumentException(
                            "Invalid CSV row on line "
                                    + lineNumber
                                    + ": expected 10 columns"
                    );
                }

                int matchWeek = parseInteger(
                        columns[3],
                        "matchweek",
                        lineNumber
                );

                String homeTeam = columns[5].trim();
                String awayTeam = columns[6].trim();

                int homeGoals = parseInteger(
                        columns[7],
                        "home goals",
                        lineNumber
                );

                int awayGoals = parseInteger(
                        columns[8],
                        "away goals",
                        lineNumber
                );

                try {

                    MatchResult match = new MatchResult(
                            matchWeek,
                            homeTeam,
                            awayTeam,
                            homeGoals,
                            awayGoals
                    );

                    matches.add(match);

                } catch (IllegalArgumentException e) {

                    throw new IllegalArgumentException(
                            "Invalid data on line "
                                    + lineNumber
                                    + ": "
                                    + e.getMessage(),
                            e
                    );
                }
            }
        }

        return matches;
    }

    private int parseInteger(
            String value,
            String field,
            int lineNumber) {

        try {

            return Integer.parseInt(value.trim());

        } catch (NumberFormatException e) {

            throw new IllegalArgumentException(
                    "Invalid "
                            + field
                            + " on line "
                            + lineNumber
                            + ": "
                            + value,
                    e
            );
        }
    }
}