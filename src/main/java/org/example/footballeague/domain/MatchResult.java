package org.example.footballeague.domain;

public class MatchResult {

    private final int matchWeek;
    private final String homeTeam;
    private final String awayTeam;
    private final int homeGoals;
    private final int awayGoals;

    public MatchResult(
            int matchWeek,
            String homeTeam,
            String awayTeam,
            int homeGoals,
            int awayGoals) {

        if (matchWeek < 1) {
            throw new IllegalArgumentException(
                    "Matchweek must be greater than 0"
            );
        }

        if (homeTeam == null || homeTeam.isBlank()) {
            throw new IllegalArgumentException(
                    "Home team cannot be empty"
            );
        }

        if (awayTeam == null || awayTeam.isBlank()) {
            throw new IllegalArgumentException(
                    "Away team cannot be empty"
            );
        }

        if (homeTeam.equalsIgnoreCase(awayTeam)) {
            throw new IllegalArgumentException(
                    "Home team and away team cannot be the same"
            );
        }

        if (homeGoals < 0) {
            throw new IllegalArgumentException(
                    "Home goals cannot be negative"
            );
        }

        if (awayGoals < 0) {
            throw new IllegalArgumentException(
                    "Away goals cannot be negative"
            );
        }

        this.matchWeek = matchWeek;
        this.homeTeam = homeTeam.trim();
        this.awayTeam = awayTeam.trim();
        this.homeGoals = homeGoals;
        this.awayGoals = awayGoals;
    }

    public int getMatchWeek() {
        return matchWeek;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public int getHomeGoals() {
        return homeGoals;
    }

    public int getAwayGoals() {
        return awayGoals;
    }
}