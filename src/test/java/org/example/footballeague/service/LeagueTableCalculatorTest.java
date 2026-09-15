package org.example.footballeague.service;

import org.example.footballeague.domain.MatchResult;
import org.example.footballeague.domain.TeamStanding;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LeagueTableCalculatorTest {

    @Test
    void homeTeamWins() {

        MatchResult match = new MatchResult(
                1,
                "Liverpool",
                "Arsenal",
                2,
                1
        );

        LeagueTableCalculator calculator =
                new LeagueTableCalculator();

        List<TeamStanding> standings =
                calculator.calculate(
                        Collections.singletonList(match)
                );

        TeamStanding liverpool =
                standings.stream()
                        .filter(team ->
                                team.getTeamName()
                                        .equals("Liverpool"))
                        .findFirst()
                        .orElseThrow();

        TeamStanding arsenal =
                standings.stream()
                        .filter(team ->
                                team.getTeamName()
                                        .equals("Arsenal"))
                        .findFirst()
                        .orElseThrow();

        assertEquals(1, liverpool.getPlayed());
        assertEquals(1, liverpool.getWon());
        assertEquals(0, liverpool.getDraw());
        assertEquals(0, liverpool.getLost());
        assertEquals(2, liverpool.getPoints());

        assertEquals(1, arsenal.getPlayed());
        assertEquals(0, arsenal.getWon());
        assertEquals(0, arsenal.getDraw());
        assertEquals(1, arsenal.getLost());
        assertEquals(0, arsenal.getPoints());
    }

    @Test
    void drawAwardsOnePointToEachTeam() {

        MatchResult match = new MatchResult(
                1,
                "Liverpool",
                "Arsenal",
                2,
                2
        );

        LeagueTableCalculator calculator =
                new LeagueTableCalculator();

        List<TeamStanding> standings =
                calculator.calculate(
                        Collections.singletonList(match)
                );

        TeamStanding liverpool =
                standings.stream()
                        .filter(team ->
                                team.getTeamName()
                                        .equals("Liverpool"))
                        .findFirst()
                        .orElseThrow();

        TeamStanding arsenal =
                standings.stream()
                        .filter(team ->
                                team.getTeamName()
                                        .equals("Arsenal"))
                        .findFirst()
                        .orElseThrow();

        assertEquals(1, liverpool.getDraw());
        assertEquals(1, arsenal.getDraw());

        assertEquals(1, liverpool.getPoints());
        assertEquals(1, arsenal.getPoints());
    }

    @Test
    void awayTeamWins() {

        MatchResult match = new MatchResult(
                1,
                "Liverpool",
                "Arsenal",
                0,
                3
        );

        LeagueTableCalculator calculator =
                new LeagueTableCalculator();

        List<TeamStanding> standings =
                calculator.calculate(
                        Collections.singletonList(match)
                );

        TeamStanding liverpool =
                standings.stream()
                        .filter(team ->
                                team.getTeamName()
                                        .equals("Liverpool"))
                        .findFirst()
                        .orElseThrow();

        TeamStanding arsenal =
                standings.stream()
                        .filter(team ->
                                team.getTeamName()
                                        .equals("Arsenal"))
                        .findFirst()
                        .orElseThrow();

        assertEquals(1, liverpool.getLost());
        assertEquals(1, arsenal.getWon());

        assertEquals(0, liverpool.getPoints());
        assertEquals(2, arsenal.getPoints());
    }

    @Test
    void teamsAreSortedByPoints() {

        MatchResult first =
                new MatchResult(
                        1,
                        "Liverpool",
                        "Arsenal",
                        3,
                        0
                );

        MatchResult second =
                new MatchResult(
                        1,
                        "Chelsea",
                        "Arsenal",
                        1,
                        0
                );

        LeagueTableCalculator calculator =
                new LeagueTableCalculator();

        List<TeamStanding> standings =
                calculator.calculate(
                        List.of(first, second)
                );

        assertEquals(
                "Liverpool",
                standings.get(0).getTeamName()
        );

        assertEquals(
                "Chelsea",
                standings.get(1).getTeamName()
        );
    }

    @Test
    void teamsWithSamePointsAreSortedByGoalAverage() {

        MatchResult liverpool =
                new MatchResult(
                        1,
                        "Liverpool",
                        "Arsenal",
                        4,
                        1
                );

        MatchResult everton =
                new MatchResult(
                        1,
                        "Everton",
                        "Chelsea",
                        2,
                        1
                );

        LeagueTableCalculator calculator =
                new LeagueTableCalculator();

        List<TeamStanding> standings =
                calculator.calculate(
                        List.of(
                                liverpool,
                                everton
                        )
                );

        assertEquals(
                "Liverpool",
                standings.get(0).getTeamName()
        );

        assertEquals(
                "Everton",
                standings.get(1).getTeamName()
        );
    }
}