package org.example.footballeague.service;

import org.example.footballeague.domain.TeamStanding;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LeagueTableSorterTest {

    @Test
    void sortsByPointsDescending() {

        TeamStanding low =
                new TeamStanding("Arsenal");

        TeamStanding high =
                new TeamStanding("Liverpool");

        low.recordMatch(0, 1);

        high.recordMatch(2, 0);

        List<TeamStanding> standings =
                new ArrayList<>(
                        List.of(low, high)
                );

        LeagueTableSorter sorter =
                new LeagueTableSorter();

        sorter.sort(standings);

        assertEquals(
                "Liverpool",
                standings.get(0).getTeamName()
        );

        assertEquals(
                "Arsenal",
                standings.get(1).getTeamName()
        );
    }

    @Test
    void usesGoalAverageWhenPointsAreEqual() {

        TeamStanding teamA =
                new TeamStanding("Liverpool");

        TeamStanding teamB =
                new TeamStanding("Everton");

        /*
         * Both receive 2 points.
         *
         * Liverpool:
         * 4 / 1 = 4.000
         *
         * Everton:
         * 2 / 1 = 2.000
         */

        teamA.recordMatch(4, 1);
        teamB.recordMatch(2, 1);

        List<TeamStanding> standings =
                new ArrayList<>(
                        List.of(teamB, teamA)
                );

        LeagueTableSorter sorter =
                new LeagueTableSorter();

        sorter.sort(standings);

        assertEquals(
                "Liverpool",
                standings.get(0).getTeamName()
        );

        assertEquals(
                "Everton",
                standings.get(1).getTeamName()
        );
    }

    @Test
    void usesTeamNameForCompleteTie() {

        TeamStanding arsenal =
                new TeamStanding("Arsenal");

        TeamStanding liverpool =
                new TeamStanding("Liverpool");

        arsenal.recordMatch(1, 1);
        liverpool.recordMatch(1, 1);

        List<TeamStanding> standings =
                new ArrayList<>(
                        List.of(liverpool, arsenal)
                );

        LeagueTableSorter sorter =
                new LeagueTableSorter();

        sorter.sort(standings);

        assertEquals(
                "Arsenal",
                standings.get(0).getTeamName()
        );

        assertEquals(
                "Liverpool",
                standings.get(1).getTeamName()
        );
    }
}