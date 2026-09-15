package org.example.footballeague.service;

import org.example.footballeague.domain.MatchResult;
import org.example.footballeague.domain.TeamStanding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeagueTableCalculator {

    private final LeagueTableSorter sorter;

    public LeagueTableCalculator() {
        this.sorter = new LeagueTableSorter();
    }

    public List<TeamStanding> calculate(
            List<MatchResult> matches) {

        if (matches == null) {
            throw new IllegalArgumentException(
                    "Matches cannot be null"
            );
        }

        Map<String, TeamStanding> standings =
                new HashMap<>();

        for (MatchResult match : matches) {

            if (match == null) {
                throw new IllegalArgumentException(
                        "Match list cannot contain null matches"
                );
            }

            TeamStanding homeStanding =
                    getOrCreateStanding(
                            standings,
                            match.getHomeTeam()
                    );

            TeamStanding awayStanding =
                    getOrCreateStanding(
                            standings,
                            match.getAwayTeam()
                    );

            homeStanding.recordMatch(
                    match.getHomeGoals(),
                    match.getAwayGoals()
            );

            awayStanding.recordMatch(
                    match.getAwayGoals(),
                    match.getHomeGoals()
            );
        }

        List<TeamStanding> result =
                new ArrayList<>(standings.values());

        return sorter.sort(result);
    }

    private TeamStanding getOrCreateStanding(
            Map<String, TeamStanding> standings,
            String teamName) {

        return standings.computeIfAbsent(
                teamName,
                TeamStanding::new
        );
    }
}