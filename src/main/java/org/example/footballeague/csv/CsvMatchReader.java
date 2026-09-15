package org.example.footballeague.csv;

import org.example.footballeague.domain.MatchResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CsvMatchReader {

    public List<MatchResult> read(Path file) throws IOException {

        List<MatchResult> matches = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(file)) {

            String line;
            boolean firstLine = true;

            while ((line = reader.readLine()) != null) {

                if (line.trim().isEmpty()) {
                    continue;
                }

                if (firstLine) {
                    firstLine = false;

                    if (line.equalsIgnoreCase(
                            "homeTeam,awayTeam,homeGoals,awayGoals")) {
                        continue;
                    }
                }

                String[] columns = line.split(",", -1);

                if (columns.length != 4) {
                    throw new IllegalArgumentException(
                            "Invalid CSV row: " + line);
                }

                String homeTeam = columns[0].trim();
                String awayTeam = columns[1].trim();

                if (homeTeam.isEmpty() || awayTeam.isEmpty()) {
                    throw new IllegalArgumentException(
                            "Team name cannot be empty: " + line);
                }

                int homeGoals;
                int awayGoals;

                try {
                    homeGoals = Integer.parseInt(columns[2].trim());
                    awayGoals = Integer.parseInt(columns[3].trim());
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException(
                            "Scores must be valid integers: " + line, e);
                }

                if (homeGoals < 0 || awayGoals < 0) {
                    throw new IllegalArgumentException(
                            "Scores cannot be negative: " + line);
                }

                MatchResult match = new MatchResult();

                match.homeTeam = homeTeam;
                match.awayTeam = awayTeam;
                match.homeGoals = homeGoals;
                match.awayGoals = awayGoals;

                matches.add(match);
            }
        }

        return matches;
    }
}