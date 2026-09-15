package org.example.footballeague.service;

import org.example.footballeague.domain.MatchResult;
import org.example.footballeague.domain.TeamStanding;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class LeagueTableCalculatorTest {

    @Test
    void homeTeamWins(){
        MatchResult match = new MatchResult();
        match.homeTeam = "Manchester City";
        match.awayTeam = "Manchester United";
        match.homeGoals = 2;
        match.awayGoals = 1;

        LeagueTableCalculator calculator = new LeagueTableCalculator();
        List<TeamStanding> standings = calculator.calculateLeagueTable(Collections.singletonList(match));

        TeamStanding manCity = standings.stream()
                .filter(team -> team.teamName.equals("Manchester City"))
                .findFirst()
                .orElseThrow();
        TeamStanding manUtd = standings.stream()
                .filter(team -> team.teamName.equals("Manchester United"))
                .findFirst()
                        .orElseThrow();
        assertEquals(2, manCity.points);
        assertEquals(0, manUtd.points);
        assertEquals(1, manCity.won);
        assertEquals(1, manUtd.lost);
        assertEquals(0, manCity.draw);
        assertEquals(0, manUtd.draw);
        assertEquals(2, manCity.goalsFor);
        assertEquals(2, manUtd.goalsAgainst);

    }
}
